package com.wizeline.gradle.practicajava.model;

import java.util.Map;

public class UserDTO {

	private String user;
	private String password;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserDTO getParameters (Map<String, String> userParam) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUser(userParam.get("user"));
		userDTO.setPassword(userParam.get("password"));
		return userDTO;
	}
	
}
