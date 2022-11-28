package com.wizeline.gradle.learningjavagradle.model;

import java.util.Map;
import java.util.Objects;

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
	
	public UserDTO getParameters(Map<String,String> userParam) {
		UserDTO user = new UserDTO();
		user.setUser(userParam.get("user"));
		user.setPassword(userParam.get("password"));
		return user;
		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return Objects.equals(user, userDTO.user) && Objects.equals(password, userDTO.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user, password);
	}
}
