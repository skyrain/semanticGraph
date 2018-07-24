package org.semantic.services;

import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.semantic.model.Movie;
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
	private static final String PREFIXES = new String("PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + "PREFIX dbo: <http://dbpedia.org/ontology/>\n"
			+ "PREFIX dbp: <http://dbpedia.org/property/>\n" + "PREFIX dbr: <http://dbpedia.org/resource/>\n"
			+ "PREFIX dct: <http://purl.org/dc/terms/>\n");
	private String DB_URL = "http://10.105.167.7:5820/instanceTypesDbo";

	@CrossOrigin
	@RequestMapping(path = "/findMovies", method = RequestMethod.POST)
	public ValidateResponse validateMatchedKnowledge(@RequestBody Movie movie) {
		test();
		return new ValidateResponse("hello");
	}

	private void test() {
		ReasoningConnection connection = ConnectionConfiguration.from(DB_URL).credentials("admin", "admin")
				.reasoning(true).connect().as(ReasoningConnection.class);

		StringBuilder query = new StringBuilder(PREFIXES);
		query.append("SELECT *" + "WHERE " + "{" + "dbr:Jackie_Powell ?a ?b" + "}");

		SelectQuery aQuery = connection.select(query.toString());
		// aQuery.parameter("s", Values.iri(newPatientIRI));
		// aQuery.parameter("type", Values.iri("http://dbpedia.org/sparql"));
		TupleQueryResult result = aQuery.execute();
		
		try {
			while (result.hasNext()) {
				System.out.println(result.next());
			}
		} catch (QueryEvaluationException e) {
			e.printStackTrace();
		}
		
		connection.close();
	}
}
