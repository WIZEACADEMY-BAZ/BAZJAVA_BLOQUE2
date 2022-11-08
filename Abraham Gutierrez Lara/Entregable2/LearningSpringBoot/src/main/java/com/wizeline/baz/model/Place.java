package com.wizeline.baz.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
	private String name;
	private String longitude;
	private String state;
	private String stateAbbreviation;
	private String latitude;
	
	public String getName() {
		return name;
	}
	@JsonProperty("place name")
	public void setName(String name) {
		this.name = name;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	@JsonProperty("state abbreviation")
	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
