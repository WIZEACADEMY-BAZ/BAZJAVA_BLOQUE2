package com.wizeline.gradle.learningjavagradle.service;

import org.springframework.http.ResponseEntity;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

public interface UserService {

	ResponseDTO createUser(String user, String password);
	
	ResponseDTO updateUser(String user, String newPassword);
	
	ResponseDTO deleteUser(String user);
	
	ResponseDTO login(String user, String password);
}
