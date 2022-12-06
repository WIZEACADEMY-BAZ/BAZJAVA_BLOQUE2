
package com.wizeline.baz.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.baz.model.request.CreateUserRequest;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.request.UpdatePasswordRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "UserController", description = "Provide User Operations and Login")
@RequestMapping("/user")
@RestController
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//simulate admin
	@PostMapping("/banker")
	public ResponseEntity<BaseResponseDTO> createUserBanker(@Valid @RequestBody CreateUserRequest request) {
		return userService.createUser(request);
	}
	
	@PreAuthorize("hasRole('BANKER')")
	@PostMapping
	public ResponseEntity<BaseResponseDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
		return userService.createUser(request);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('BANKER')" )
	@PutMapping("/password")
	public ResponseEntity<BaseResponseDTO> updatePassword(@RequestBody UpdatePasswordRequest request) {
		return userService.updateUserPassword(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<BaseResponseDTO> login(@Valid @RequestBody LoginRequest request) {
		return userService.login(request);
	}
	
	@PreAuthorize("hasRole('BANKER')")
	@GetMapping
	public ResponseEntity<BaseResponseDTO> getUsers() {
		return userService.getUsers();
	}
	    
}