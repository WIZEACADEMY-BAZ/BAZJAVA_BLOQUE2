package com.wizeline.service;

import com.wizeline.model.ResponseDTO;

public interface UserService {
	
	ResponseDTO createUser(String user, String password);
	
	ResponseDTO login(String user, String password);

}
