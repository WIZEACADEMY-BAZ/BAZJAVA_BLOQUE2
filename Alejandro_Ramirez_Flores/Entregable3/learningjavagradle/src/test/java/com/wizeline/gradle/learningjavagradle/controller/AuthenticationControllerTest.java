package com.wizeline.gradle.learningjavagradle.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.wizeline.gradle.learningjavagradle.model.UserDTO;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
	
	private static final Logger LOGGER = Logger.getLogger(AuthenticationControllerTest.class.getName());
	
	    @InjectMocks
	    private AuthenticationController authenticationController;

	    @Mock
	    private UserDetailsService userDetailsService;

	@Test
	void testGetAuthenticationToken() {
		LOGGER.info("Preparando Prueba del Controller: getAuthenticationToken()");
		UserDTO userDTO = new UserDTO();
        UserDetails userDetails = null;
        when(userDetailsService.loadUserByUsername(userDTO.getUsers())).thenReturn(userDetails);
        ResponseEntity response = authenticationController.getAuthenticationToken(userDTO);
        assertTrue(response.getStatusCode().is2xxSuccessful(), "Codigo HTTP EXITOSO");
	}
}
