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

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationControllerTest.class.getName());
    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserDetailsService userDetailsService;


    @Test
    void getAuthenticationToken() {
        //Prepara el esenario de la prueba
        LOGGER.info("Entrando al metodo getAuthenticationToken . . ");
        UserDTO userDTO = new UserDTO();
        UserDetails userDetails = null;
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenReturn(userDetails);
        //Ejecuta la logica a probar
        ResponseEntity u = authenticationController.getAuthenticationToken(userDTO);

        //llamadas
        assertTrue(u.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
        LOGGER.info("Prueba correctamente . . . ");
    }
}