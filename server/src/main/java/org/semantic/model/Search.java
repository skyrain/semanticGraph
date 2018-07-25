package org.semantic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Search {
	private String name;

	public Search(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
