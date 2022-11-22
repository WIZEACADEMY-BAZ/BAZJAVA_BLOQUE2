package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.config.JwtTokenConfig;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerTest.class);

    @Mock
    UserDetails userDetails;

    @MockBean
    JwtTokenConfig jwtTokenConfig;

    @MockBean
    UserDetailsService userDetailsService;

    @Autowired
    AuthenticationController authenticationController;

    @Mock
    UserDTO userDTO;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAuthenticationTokenTest() {
        LOGGER.info("getAutenticationToken Testing...");
        when(userDetailsService.loadUserByUsername(userDTO.getUser())).thenReturn(userDetails);

        Claims claims = Jwts.claims().setSubject(userDTO.getUser());
        claims.put("username", userDTO.getUser());
        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("date", new Date());

        String token = jwtTokenConfig.generateToken(userDTO, claims);

        assertAll(
                () -> assertNotNull(authenticationController.getAuthenticationToken(userDTO)),
                //() -> assertNotNull(token),
                () -> assertEquals(authenticationController.getAuthenticationToken(userDTO).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    public void getAuthenticationTokenFailedTest() {
        LOGGER.info("getAuthenticationTokenFailed Testing...");
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Error");

        when(userDetailsService.loadUserByUsername(any())).thenThrow(usernameNotFoundException);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> authenticationController.getAuthenticationToken(userDTO),
                "Ocurrió un error");

        assertEquals(exception.getStatus(), HttpStatus.UNAUTHORIZED);
    }
}
