package com.wizeline.gradle.learningjavagradle.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
private static final Logger LOGGER = Logger.getLogger(UserServiceImplTest.class.getName());
	
	private MockMvc mockMvc;
	
	@Spy
	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	void testCreateUser() {
		ResponseDTO response = userServiceImpl.createUser("user1@wizeline.com", "pass1");
		assertNotNull(response);
	}

	@Test
	void testLogin() {
		ResponseDTO response = userServiceImpl.login("user1@wizeline.com", "pass1");
		assertNotNull(response);
		
		when(userServiceImpl.login("user1@wizeline.com", "pass1"))
        .thenReturn(new ResponseDTO());

		assertEquals(new ResponseDTO(), userServiceImpl.login("user1@wizeline.com", "pass1"));
	}

	@Test
	void testCrearUsuarios() {
		//ResponseDTO response2=new ResponseDTO();
		 ArrayList<UserDTO> usuarios = new ArrayList<>();
		ResponseDTO response1 = userServiceImpl.crearUsuarios(usuarios);
		assertNotNull(response1);
		
	}

}
