package com.wizeline.learningspring.controller;

import com.wizeline.learningspring.configuration.JwtTokenConfig;
import com.wizeline.learningspring.model.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
class AuthenticationControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationControllerTest.class);
    @Autowired
    private AuthenticationController controller;
    @MockBean
    private UserDetailsService service;
    @MockBean
    private JwtTokenConfig jwtTokenConfig;
    @Mock
    private UserDetails userDetail;

    @Test
    void getAuthenticationTokenTest() {
        LOGGER.info("getAuthenticationTokenTest");
        UserDTO userDTO = new UserDTO("usuarioTest", "passwordTest");

        when(service.loadUserByUsername(userDTO.getUser())).thenReturn(userDetail);

        Claims claims = Jwts.claims().setSubject(userDTO.getUser());
        claims.put("username", userDTO.getUser());
        String authorities = userDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put("authorities", authorities);
        claims.put("date", new Date());
        String token = "56998b46d8b54aa2531f4fc9e4d7486bd0f31148";

        when(jwtTokenConfig.generateToken(userDTO, claims)).thenReturn(token);

        assertAll(
                () -> assertNotNull(controller.getAuthenticationToken(userDTO)),
                () -> assertEquals("56998b46d8b54aa2531f4fc9e4d7486bd0f31148", token),
                () -> assertEquals(controller.getAuthenticationToken(userDTO).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getAuthenticationTokenExcepcion() {
        LOGGER.info("getAuthenticationTokenExcepcion");
        UserDTO userDTO = new UserDTO("usuarioTest", "passwordTest");
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Error");
        when(service.loadUserByUsername(any())).thenThrow(usernameNotFoundException);
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> controller.getAuthenticationToken(userDTO),
                "Conversion incorrecta"
        );
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatus());
    }
}