package com.wizeline.gradle.learningjavagradle.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomPassword {
	
	String randomPassword;

	public String getRandomPassword() {
		return randomPassword;
	}

	@JsonProperty("password")
	public void setRandomPassword(String randomPassword) {
		this.randomPassword = randomPassword;
	}
	
	

}
