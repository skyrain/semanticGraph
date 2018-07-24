package org.semantic.entrypoint;
import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Initialization implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// load ontology
		OWLOntology schema = null;

		try {
			schema = OWLManager.createOWLOntologyManager()
					.loadOntologyFromOntologyDocument(new File("TODO"));
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
}