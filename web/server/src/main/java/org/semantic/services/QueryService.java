package org.semantic.services;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryService {

	@CrossOrigin
	@RequestMapping(path = "/findMovies", method = RequestMethod.POST)
	public ValidateResponse validateMatchedKnowledge(@RequestBody String movie) {		
		return null;
	}
}

