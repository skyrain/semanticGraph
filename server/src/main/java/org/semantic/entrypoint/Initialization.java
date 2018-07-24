package org.semantic.entrypoint;

import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.TupleQueryResult;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.complexible.common.rdf.model.Values;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.api.SelectQuery;
import com.complexible.stardog.api.reasoning.ReasoningConnection;

@Component
public class Initialization implements ApplicationRunner {

    private static final String ENDPOINT = "https://dbpedia.org/sparql";
    private static final String PREFIXES = new String(
            "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n" +
            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
            "PREFIX dbo: <http://dbpedia.org/ontology/>\n" +
            "PREFIX dbp: <http://dbpedia.org/property/>\n" +
            "PREFIX dbr: <http://dbpedia.org/resource/>\n" +
            "PREFIX dct: <http://purl.org/dc/terms/>\n"
    );
	
    private String DB_BASIC_ONTOLOGY_URL = "http://10.105.167.7:5820/basicOntology";
	private String DB_DBO_URL = "http://10.105.167.7:5820/instanceTypesDbo";
	private String DB_EXT_URL = "http://10.105.167.7:5820/instanceTypesExt";

	@Override
	public void run(ApplicationArguments arg0) {
		test();
	}

	private void test() {
		String diagnosis = null;
		ReasoningConnection connection = ConnectionConfiguration.from(DB_DBO_URL).credentials("admin", "admin")
				.reasoning(true).connect().as(ReasoningConnection.class);		

		StringBuilder query = new StringBuilder(PREFIXES);
		query.append("SELECT *"				
				+ "WHERE "
				+ "{"
				+ "<http://dbpedia.org/resource/Jackie_Powell> ?a ?b"
				+ "}");
		
		SelectQuery aQuery = connection
				.select(query.toString());
//		aQuery.parameter("s", Values.iri(newPatientIRI));
	//	aQuery.parameter("type", Values.iri("http://dbpedia.org/sparql"));
		TupleQueryResult result = aQuery.execute();
		try {
			while (result.hasNext()) {
				System.out.println(result.next());
			}
		} catch (QueryEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	//	diagnosis = reasonResult.hasNext() ? "是痛风病人" : "不是痛风病人";

		
		connection.close();
	}
}