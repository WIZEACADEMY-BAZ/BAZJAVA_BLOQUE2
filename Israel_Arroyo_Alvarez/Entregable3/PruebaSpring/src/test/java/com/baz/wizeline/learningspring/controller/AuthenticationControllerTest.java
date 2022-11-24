package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.config.JwtTokenConfig;
import com.baz.wizeline.learningspring.model.UserDTO;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAuthenticationTokenTest() {

        String token = null;
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenReturn(userDetails);
        when(jwtTokenConfig.generateToken(userDTO, claims)).thenReturn(token);
        assertAll(
                () -> assertEquals(authenticationController.getAuthenticationToken(userDTO).getBody(), token),
                () -> assertEquals(authenticationController.getAuthenticationToken(userDTO).getStatusCodeValue(), HttpStatus.OK.value())
        );
    }

}
