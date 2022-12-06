package com.wizeline.baz.service;

import org.springframework.http.ResponseEntity;

import com.wizeline.baz.model.request.CreateUserRequest;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.request.UpdatePasswordRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;

public interface UserService {
	ResponseEntity<BaseResponseDTO> createUserBanker(CreateUserRequest request);
	ResponseEntity<BaseResponseDTO> createUser(CreateUserRequest request);
	ResponseEntity<BaseResponseDTO> updateUserPassword(UpdatePasswordRequest request);
	ResponseEntity<BaseResponseDTO> login(LoginRequest request);
	ResponseEntity<BaseResponseDTO> getUsers();
}
