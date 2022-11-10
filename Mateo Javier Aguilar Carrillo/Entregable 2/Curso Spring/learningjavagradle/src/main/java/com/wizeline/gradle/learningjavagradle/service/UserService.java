package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

public interface UserService {

	ResponseDTO createUser(String user, String password);
	
	ResponseDTO createUser(String user);
	
	ResponseDTO updateUser(String user, String newPassword);
	
	ResponseDTO deleteUser(String user);
	
	ResponseDTO login(String user, String password);
}
