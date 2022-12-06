package com.wizeline.baz.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.wizeline.baz.utils.Constants;

public class CreateUserRequest {
	
	@NotEmpty
	private String email;
	@NotEmpty
	private String name;
	@NotEmpty
	@Pattern(regexp = Constants.PASSWORD_PATTERN, message = "La contrasena debe cumplir con el patron")
	private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
