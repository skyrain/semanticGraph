package org.semantic.services;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
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
			+ "PREFIX dct: <http://purl.org/dc/terms/>"
			+ "PREFIX skos: <http://www.w3.org/2004/02/skos/core#> ");
	
	private String DB_URL = "http://localhost:5820/personDB";

	@CrossOrigin
	@RequestMapping(path = "/graphExplore", method = RequestMethod.POST)
	public List<KnowledgeResponse> validateMatchedKnowledge(@RequestBody Search search) {
		return initSearch(search);
	}

	private List<KnowledgeResponse> initSearch(Search search) {
		List<KnowledgeResponse> ret = new ArrayList<>();
		
		ReasoningConnection connection = ConnectionConfiguration.from(DB_URL).credentials("admin", "admin")
				.reasoning(true).connect().as(ReasoningConnection.class);

		String criteria = normalizeSearch(search.getName());
		StringBuilder query = new StringBuilder(PREFIXES);
		query.append(
				"select * " +
				"where" +
				"{  " +
				"    optional{" +
				"        dbr:"+ criteria + " foaf:homepage ?homePage." +
				"    }" +
				"    optional{" +
				"        dbr:Category:"+ criteria + " rdfs:label ?label." +
				"    }" +
				"    optional{ " +
				"        dbr:Category:"+ criteria + " skos:broader ?broader1." +
				"    }" +
				"    optional{" +
				"        ?broader1 foaf:homepage ?broader1HomePage ." +
				"    }" +
				"    optional{" +
				"        ?broader1 rdfs:label ?broader1Label .    " +
				"    }" +
				"    optional{" +
				"        ?broader1 skos:broader ?broader2 ." +
				"    }" +
				"    optional{" +
				"        ?broader2 foaf:homepage ?broader2HomePage ." +
				"    }" +
				"    optional{" +
				"        ?broader2 rdfs:label ?broader2Label ." +
				"    }" +
				"	optional {" +
				"		dbr:" + criteria +" owl:sameAs ?originalGeoLink ." +
				"		FILTER (NOT EXISTS{dbr:" + criteria + " rdf:type dbo:Settlement})" +
				"	}" +
				"}");

		SelectQuery selectQuery = connection.select(query.toString());
		TupleQueryResult result = selectQuery.execute();
		
		try {
			while (result.hasNext()) {
				String val = result.next().toString();
				
				if(val != null){
					ret.add(resultBuilder(val));
					System.out.println(val);
				}
			}
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		connection.close();
		return ret;
	}
	
	private KnowledgeResponse resultBuilder(String val) {			
		int homePageIdx = val.indexOf("homePage");
		homePageIdx = homePageIdx  == -1? -1: homePageIdx + "homePage".length();
		int labelIdx = val.indexOf("label");
		labelIdx = labelIdx  == -1? -1: labelIdx + "label".length(); 
		int broader1Idx = val.indexOf("broader1");
		broader1Idx = broader1Idx  == -1? -1: broader1Idx + "broader1".length(); 
		int broader1HomePageIdx = val.indexOf("broader1HomePage");
		broader1HomePageIdx = broader1HomePageIdx  == -1? -1: broader1HomePageIdx + "broader1HomePage".length(); 
		int broader1LabelIdx = val.indexOf("broader1Label");
		broader1LabelIdx = broader1LabelIdx  == -1? -1: broader1LabelIdx + "broader1Label".length(); 
		int broader2Idx = val.indexOf("broader2");
		broader2Idx = broader2Idx  == -1? -1: broader2Idx + "broader2".length(); 
		int broader2HomePageIdx = val.indexOf("broader2HomePage");
		broader2HomePageIdx = broader2HomePageIdx  == -1? -1: broader2HomePageIdx + "broader2HomePage".length(); 
		int broader2LabelIdx = val.indexOf("broader2Label");
		broader2LabelIdx = broader2LabelIdx == -1? -1: broader2LabelIdx + "broader2Label".length();
		
		int originalGeoLinkIdx = val.indexOf("originalGeoLink");
		originalGeoLinkIdx = originalGeoLinkIdx == -1? -1: originalGeoLinkIdx + "originalGeoLink".length() + 1;
		String originalGeoLink = originalGeoLinkIdx == -1? null: val.substring(originalGeoLinkIdx, val.length() - 1);
		
		return new KnowledgeResponse(responseBuilder(homePageIdx, val),
				responseBuilder(labelIdx, val),
				responseBuilder(broader1Idx, val),
				responseBuilder(broader1HomePageIdx, val),
				responseBuilder(broader1LabelIdx, val),
				responseBuilder(broader2Idx, val),
				responseBuilder(broader2HomePageIdx, val),
				responseBuilder(broader2LabelIdx, val),
				originalGeoLink
				);
	}

	private String responseBuilder(int propertyIdx, String val) {
		if(propertyIdx != -1){
			int nextColonIdx = val.indexOf(";", propertyIdx);
			
			if(nextColonIdx == -1){
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
				else
					arr[i] -= 32;
			arr[j] = arr[i];
		}

		res = String.valueOf(arr);
		res = res.substring(1, j - 1);
		return res;
	}
	
}
