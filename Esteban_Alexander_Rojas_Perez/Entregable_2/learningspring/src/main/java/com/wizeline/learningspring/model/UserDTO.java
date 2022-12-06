package com.wizeline.learningspring.model;

import java.util.Map;

public class UserDTO {
	private String user;
	private String password;

	public UserDTO() {
	}

	public UserDTO(String user, String password) {
		this.user = user;
		this.password = password;
	}

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
	
	public UserDTO getParameters(Map<String, String> userParam) {
		UserDTO user = new UserDTO();
		user.setUser(userParam.get("user"));
		user.setPassword(userParam.get("password"));
		return user;
	}
}
