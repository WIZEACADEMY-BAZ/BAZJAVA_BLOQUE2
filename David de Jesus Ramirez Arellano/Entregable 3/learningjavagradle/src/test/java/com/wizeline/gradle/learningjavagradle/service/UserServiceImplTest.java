package com.wizeline.gradle.learningjavagradle.service;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserServiceImpl.class)
class UserServiceImplTest {
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	UserServiceImpl service;
	
	@Test
	@DisplayName("createUser desde service correcto")
	void createUser() {
		LOGGER.info("Creacion de nuevo usuario");
		service.createUser("david", "password");
	}

	@Test
	@DisplayName("createUser desde service incorrecto")
	void createUserIn() {
		LOGGER.info("Creacion de nuevo usuario con parametros nulos");
		service.createUser(null, null);
	}
	
	@Test
	@DisplayName("login desde service correcto")
	void login() {
		LOGGER.info("Login de usuario correcto");
		service.login("david", "password");
	}

	@Test
	@DisplayName("login desde service incorrecto")
	void loginIn() {
		LOGGER.info("Login incorrecto por que los parametros son nulos");
		service.login(null, null);
	}
}
