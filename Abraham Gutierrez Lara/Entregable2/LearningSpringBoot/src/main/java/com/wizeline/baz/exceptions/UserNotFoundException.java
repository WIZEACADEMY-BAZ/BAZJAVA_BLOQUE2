package com.wizeline.baz.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final String user;
	
	public UserNotFoundException(String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}	
}
