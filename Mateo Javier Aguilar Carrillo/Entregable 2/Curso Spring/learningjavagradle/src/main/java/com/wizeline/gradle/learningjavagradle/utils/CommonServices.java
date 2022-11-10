package com.wizeline.gradle.learningjavagradle.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.service.UserService;

@Component
public class CommonServices {

	@Autowired
	UserService userService;

	public ResponseDTO login(String user, String password) {
		return userService.login(user, password);
	}

}