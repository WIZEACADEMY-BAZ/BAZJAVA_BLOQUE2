package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ResponseDTO responseDTO;

    @InjectMocks
    private UserController userController;

    @BeforeEach()
    void init(){
        MockitoAnnotations.openMocks(this);
        System.out.println("@BeforeEach => iniciando");
    }

    @Test
    void loginTest() {
        System.out.println("@Test => loginTest()");
        when(userService.getUserMongo("user", "password")).thenReturn(responseDTO);
        assertAll(
                () -> assertNotNull(userController.login("user", "password")),
                () -> assertEquals(userController.login("user", "password").getStatusCode().value(), HttpStatus.OK.value())
        );
    }
}