package com.wizeline.gradle.practicajava.service;

import com.wizeline.gradle.practicajava.model.ResponseDTO;

public interface UserService {

	ResponseDTO createUser(String user, String password);

	ResponseDTO login(String user, String password);

}
