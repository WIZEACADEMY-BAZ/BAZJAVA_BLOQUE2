package com.wizeline.baz.model.response;

import com.wizeline.baz.model.UserDTO;

public class CreateUserResponse extends BaseResponseDTO {
	private UserDTO user;

	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	
}
