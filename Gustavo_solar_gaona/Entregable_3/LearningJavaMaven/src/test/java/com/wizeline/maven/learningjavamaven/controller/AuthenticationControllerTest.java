package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.configuration.JwtTokenConfig;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private UserDTO userDTO;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtTokenConfig jwtTokenConfig;

    @Mock
    private Claims claims;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void init(){
        System.out.println("@BeforeEach => iniciando");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthenticationTokenTest(){
        System.out.println("@Test => getAuthenticationTokenTest()");
        String token = null;
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenReturn(userDetails);
        when(jwtTokenConfig.generateToken(userDTO, claims)).thenReturn(token);
        assertAll(
                () -> assertEquals(authenticationController.getAuthenticationToken(userDTO).getBody(), token),
                () -> assertEquals(authenticationController.getAuthenticationToken(userDTO).getStatusCodeValue(), HttpStatus.OK.value())
        );
    }

    @Test
    void getAuthenticationTokenExceptionTest(){
        System.out.println("@Test => getAuthenticationTokenExceptionTest()");
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenThrow(UsernameNotFoundException.class);
        assertAll(
                () -> assertThrows(ResponseStatusException.class, () -> authenticationController.getAuthenticationToken(userDTO),"Se lanza la exepcion de usuario NOT FUND")
        );
    }

}