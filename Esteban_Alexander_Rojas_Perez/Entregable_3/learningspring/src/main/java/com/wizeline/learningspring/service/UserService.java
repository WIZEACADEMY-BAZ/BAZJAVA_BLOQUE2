package com.wizeline.learningspring.service;

import com.wizeline.learningspring.model.ResponseDTO;

public interface UserService {
	ResponseDTO createUser(String user, String password);
	ResponseDTO login(String user, String password);
}
