package com.wizeline.gradle.learningjavagradle.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@MockBean
	private UserService service;

	@Autowired
	private MockMvc mockMvc;
	
	 @Autowired
	  private ObjectMapper objectMapper;
	
	@Test
	@DisplayName("login")
	void LoginCorrecto() throws Exception {
		LOGGER.info("Prueba de login desde el controller");
		String user= "david";
		String password="pass";
		ResponseDTO response =new ResponseDTO("success","OK000");
		Mockito.when(service.login(user, password)).thenReturn(response);
		mockMvc.perform(
	      get("/api/login/david/pass").accept(MediaType.APPLICATION_JSON_VALUE))
	     .andExpect(status().isOk())
	     .andReturn();
	}
	
	@Test
	@DisplayName("createUser")
	void createUserCorrecto() throws Exception {
		LOGGER.info("Prueba de creación de usuario desde el controller");
		ResponseDTO response =new ResponseDTO("success","OK000");
		Mockito.when(service.createUser("david", "password")).thenReturn(response);
		String usuario = objectMapper.writeValueAsString(new UserDTO("david","password"));
		mockMvc.perform(
	      post("/api/createUser").content(usuario).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
	     .andExpect(status().isOk())
	     .andReturn();
	}
	
	
}
