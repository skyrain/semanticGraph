package org.semantic.services;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.semantic.model.InfoResponse;
import org.semantic.model.Search;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;

@RestController
public class QueryService {
	private static final String PREFIXES = new String("PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" + "PREFIX dbo: <http://dbpedia.org/ontology/>"
			+ "PREFIX dbp: <http://dbpedia.org/property/>" + "PREFIX dbr: <http://dbpedia.org/resource/>"
			+ "PREFIX dct: <http://purl.org/dc/terms/>" + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ");

	private String DB_URL = "http://localhost:5820/personDB";
	
	private String DBR = "http://dbpedia.org/resource/";
	private String DBR_CATEGORY = "http://dbpedia.org/resource/Category:";

	@CrossOrigin
	@RequestMapping(path = "/basicExplore", method = RequestMethod.POST)
	public List<InfoResponse> basicExlpore(@RequestBody Search search) {
		String criteria = normalizeSearch(search.getName());
		return search(buildBasicQuery(criteria), null);
	}

	@CrossOrigin
	@RequestMapping(path = "/broaderExplore", method = RequestMethod.POST)
	public List<InfoResponse> broaderExplore(@RequestBody Search search) {
		String criteria = normalizeSearch(search.getName());
		return search(buildBroaderQuery(criteria), "broader");
	}

	@CrossOrigin
	@RequestMapping(path = "/narrowerExplore", method = RequestMethod.POST)
	public List<InfoResponse> narrowerExplore(@RequestBody Search search) {
		String criteria = normalizeSearch(search.getName());
		return search(buildNarrowerQuery(criteria), "narrower");
	}
	
	private List<InfoResponse> search(String detailQuery, String resourceName) {
		List<InfoResponse> ret = new ArrayList<>();

		ReasoningConnection connection = ConnectionConfiguration.from(DB_URL).credentials("admin", "admin")
				.reasoning(true).connect().as(ReasoningConnection.class);

		StringBuilder query = new StringBuilder(PREFIXES);
		query.append(detailQuery);

		SelectQuery selectQuery = connection.select(query.toString());
		TupleQueryResult result = selectQuery.execute();

		try {
			while (result.hasNext()) {
				String val = result.next().toString();

				if (val != null) {
					ret.add(resultBuilder(val, resourceName));
					System.out.println(val);
				}
			}
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}

		connection.close();
		return ret;
	}

	private InfoResponse resultBuilder(String val, String resourceName) {
		int resourceNameIdx = resourceName == null? -1: val.indexOf(resourceName);
		resourceNameIdx = resourceNameIdx == -1 ? -1 : resourceNameIdx + resourceName.length();
		
		int homePageIdx = val.indexOf("homePage");
		homePageIdx = homePageIdx == -1 ? -1 : homePageIdx + "homePage".length();
		int labelIdx = val.indexOf("label");
		labelIdx = labelIdx == -1 ? -1 : labelIdx + "label".length();
		int geoLinkIdx = val.indexOf("geoLink");
		geoLinkIdx = geoLinkIdx == -1 ? -1 : geoLinkIdx + "geoLink".length();

		return new InfoResponse(responseBuilder(resourceNameIdx, val), responseBuilder(homePageIdx, val), responseBuilder(labelIdx, val),
				responseBuilder(geoLinkIdx, val));
	}

	private String responseBuilder(int propertyIdx, String val) {
		if (propertyIdx != -1) {
			int nextColonIdx = val.indexOf(";", propertyIdx);

			if (nextColonIdx == -1) {
				return val.substring(propertyIdx + 1, val.length() - 1);
			}
			return val.substring(propertyIdx + 1, nextColonIdx);
		}
		return null;
	}

	public String normalizeSearch(String str) {
		String res = "_" + str + "_";
		res = res.replace(' ', '_');
		res = res.toLowerCase();
		char[] arr = res.toCharArray();

		int i, j;
		for (i = 1, j = 1; i < arr.length; i++, j++) {
			if (arr[i - 1] == '_')
				if (arr[i] == '_')
					j--;
			arr[j] = arr[i];
		}

		res = String.valueOf(arr);
		res = res.substring(1, j - 1);
		return res;
	}

	private String buildBasicQuery(String criteria) {
		return 				
		"select * " +
		"where" +
		"{  " +
		" 	?existCriteria foaf:homepage ?homePage ." +
		"   FILTER (lcase(str(?existCriteria)) = lcase(\"" + DBR + criteria + "\"))" +		
		"   optional{" +
		" 		?existLabel rdfs:label ?label ." +
		"   	FILTER (lcase(str(?existCriteria)) = lcase(\"" + DBR_CATEGORY + criteria + "\"))" +
		"    }" +
		"	optional {" +
		" 		?existGeoLink  owl:sameAs ?geoLink ." +
		"   	FILTER (lcase(str(?existGeoLink)) = lcase(\"" + DBR + criteria + "\"))" +
		// NOT EXISTS here unfortunately means exists
		"		FILTER (NOT EXISTS{?existGeoLink rdf:type dbo:Settlement})" +
		"	}" +
		"}";
	}

	private String buildBroaderQuery(String criteria) {
		return 
				"select * " +
				"where" +
				"{  " +
				" 	?existCriteria  skos:broader ?broader." +
				"   FILTER (lcase(str(?existCriteria)) = lcase(\"" + DBR_CATEGORY + criteria + "\"))" +	
				"    optional{" +
				"        ?broader foaf:homepage ?homePage ." +
				"    }" +
				"    optional{" +
				"        ?broader rdfs:label ?label .    " +
				"    }" +
				"}LIMIT 100";
	}
	
	private String buildNarrowerQuery(String criteria) {
		return 
				"select * " +
				"where" +
				"{  " +
				" 	?narrower skos:broader ?existCriteria ." +
				"   FILTER (lcase(str(?existCriteria)) = lcase(\"" + DBR_CATEGORY + criteria + "\"))" +
				"    optional{" +
				"        ?narrower foaf:homepage ?homePage ." +
				"    }" +
				"    optional{" +
				"        ?narrower rdfs:label ?label .    " +
				"    }" +
				"}LIMIT 100";
	}
}
