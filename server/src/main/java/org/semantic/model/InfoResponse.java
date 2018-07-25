package org.semantic.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InfoResponse {
	private String homePage;
	private String label;
	private String geoLink;

	public InfoResponse(@JsonProperty("homePage") String homePage, @JsonProperty("label") String label,
			@JsonProperty("geoLink") String geoLink) {
		this.homePage = homePage;
		this.label = label;
		this.geoLink = geoLink;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getGeoLink() {
		return geoLink;
	}

	public void setGeoLink(String geoLink) {
		this.geoLink = geoLink;
	}
}