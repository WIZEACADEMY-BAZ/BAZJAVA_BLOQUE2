package com.wizeline.baz.model.response;

import java.util.List;

import com.wizeline.baz.model.UserDTO;

public class GetUsersResponse extends BaseResponseDTO {
	private List<UserDTO> users;

	public List<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}
}
