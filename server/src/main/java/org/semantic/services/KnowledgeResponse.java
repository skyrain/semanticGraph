package org.semantic.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KnowledgeResponse {
	private String homePage;
	private String label;
	private String broader1;
	private String broader1HomePage;
	private String broader1Label;
	private String broader2;
	private String broader2HomePage;
	private String broader2Label;
	private String originalGeoLink;
	
	public KnowledgeResponse(@JsonProperty("homePage") String homePage, @JsonProperty("label") String label,
			@JsonProperty("broader1") String broader1, @JsonProperty("broader1HomePage") String broader1HomePage,
			@JsonProperty("broader1Label") String broader1Label, @JsonProperty("broader2") String broader2,
			@JsonProperty("broader2HomePage") String broader2HomePage,
			@JsonProperty("broader2Label") String broader2Label,
			@JsonProperty("originalGeoLink") String originalGeoLink) {
		this.homePage = homePage;
		this.label = label;
		this.broader1 = broader1;
		this.broader1HomePage = broader1HomePage;
		this.broader1Label = broader1Label;
		this.broader2 = broader2;
		this.broader2HomePage = broader2HomePage;
		this.broader2Label = broader2Label;
		this.originalGeoLink = originalGeoLink;
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

	public String getBroader1() {
		return broader1;
	}

	public void setBroader1(String broader1) {
		this.broader1 = broader1;
	}

	public String getBroader1HomePage() {
		return broader1HomePage;
	}

	public void setBroader1HomePage(String broader1HomePage) {
		this.broader1HomePage = broader1HomePage;
	}

	public String getBroader1Label() {
		return broader1Label;
	}

	public void setBroader1Label(String broader1Label) {
		this.broader1Label = broader1Label;
	}

	public String getBroader2() {
		return broader2;
	}

	public void setBroader2(String broader2) {
		this.broader2 = broader2;
	}

	public String getBroader2HomePage() {
		return broader2HomePage;
	}

	public void setBroader2HomePage(String broader2HomePage) {
		this.broader2HomePage = broader2HomePage;
	}

	public String getBroader2Label() {
		return broader2Label;
	}

	public void setBroader2Label(String broader2Label) {
		this.broader2Label = broader2Label;
	}

	public String getOriginalGeoLink() {
		return originalGeoLink;
	}

	public void setOriginalGeoLink(String originalGeoLink) {
		this.originalGeoLink = originalGeoLink;
	}
}