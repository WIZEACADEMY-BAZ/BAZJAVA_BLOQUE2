package com.wizeline.maven.learningjava.Learning.controller;

import com.wizeline.maven.learningjava.Learning.config.JwtTokenConfig;
import com.wizeline.maven.learningjava.Learning.model.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    AuthenticationController authenticationController;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    JwtTokenConfig jwtTokenConfig;

    @Test
    @DisplayName("Prueba de token exitoso")
    void getAuthenticationToken() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("user");
        userDTO.setPassword("123");
        GrantedAuthority authority = new SimpleGrantedAuthority("Admin");
        UserDetails userDetails = new User("alex", "", Collections.singletonList(authority));
        when(userDetailsService.loadUserByUsername(Mockito.any())).thenReturn(userDetails);
        when(jwtTokenConfig.generateToken(Mockito.any(), Mockito.any())).thenReturn("token");
        ResponseEntity<?> response = authenticationController.getAuthenticationToken(userDTO);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Prueba de token excepcion")
    void getAuthenticationTokenExcepcion() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("user");
        userDTO.setPassword("123");
        GrantedAuthority authority = new SimpleGrantedAuthority("Admin");
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Error");
        when(userDetailsService.loadUserByUsername(Mockito.any())).thenThrow(usernameNotFoundException);
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> authenticationController.getAuthenticationToken(userDTO),
                "Conversion incorrecta"
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatus());
    }
}