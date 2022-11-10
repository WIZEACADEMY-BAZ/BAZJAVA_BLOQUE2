package com.wizeline.model;

import java.util.Map;

public class UserDTO {
	
	private String user;
	private String password;
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getUser() {
		return user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public UserDTO getParameters(Map<String, String> userParam) {
		UserDTO user = new UserDTO();
		user.setPassword(userParam.get("password"));
		user.setUser(userParam.get("user"));
		return user;
	}

}
