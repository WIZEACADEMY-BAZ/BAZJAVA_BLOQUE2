package com.wizeline.baz.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostalCodeInfo {
	private String postalCode;
	private String country;
	private String countryAbbreviation;
	private Place[] places;
	
	public String getPostalCode() {
		return postalCode;
	}
	@JsonProperty("post code")
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}
	@JsonProperty("country abbreviation")
	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}
	public Place[] getPlaces() {
		return places;
	}
	public void setPlaces(Place[] places) {
		this.places = places;
	}
}
