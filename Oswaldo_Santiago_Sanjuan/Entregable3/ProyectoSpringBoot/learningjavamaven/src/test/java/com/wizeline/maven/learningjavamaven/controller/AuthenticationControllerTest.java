package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserDetailsService userDetailsService;


    @Test
    void getAuthenticationToken() {
        //Prepara el esenario de la prueba
        UserDTO userDTO = new UserDTO();
        UserDetails userDetails = null;
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenReturn(userDetails);
        //Ejecuta la logica a probar
        ResponseEntity u = authenticationController.getAuthenticationToken(userDTO);

        //llamadas
        assertTrue(u.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");

    }
}