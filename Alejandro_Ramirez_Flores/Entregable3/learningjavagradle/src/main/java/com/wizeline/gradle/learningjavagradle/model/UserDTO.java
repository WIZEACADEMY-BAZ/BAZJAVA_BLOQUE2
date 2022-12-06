package com.wizeline.gradle.learningjavagradle.model;

import java.util.Map;

public class UserDTO {
    private String users;
    private String password;
    
   // UserDTO user = new UserDTO();


    public UserDTO() {
		super();
	}

	public String getUsers() {
		return users;
	}



	public void setUsers(String users) {
		this.users = users;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public UserDTO getParameters(Map<String, String> userParam){
        UserDTO user = new UserDTO();
        user.setUsers(userParam.get("user"));
        user.setPassword(userParam.get("password"));
        return user;
    }
}
