package com.wizeline.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.model.ResponseDTO;
import com.wizeline.model.UserDTO;
import com.wizeline.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private static final Logger log = Logger.getLogger(UserController.class.getName());
	
	@GetMapping(value="/login", produces="application/json")
	public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
		log.info("LearningJava - Procesando peticion HTTP Get. Starting...");
		ResponseDTO response = new ResponseDTO();
		response = userService.login(user, password);
		log.info("Login completado");
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
		
	}
	
	@PostMapping(value="/createUser", produces="application/json")
	public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request){
		log.info("LearningJava - Procesando peticion HTTP Post. Starting...");
		ResponseDTO response = new ResponseDTO();
		response = userService.createUser(request.getUser(), request.getPassword());
		log.info("Create user completado");
		return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
	}
	

}
