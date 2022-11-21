package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    UserDTO userDTO;

    @Mock
    ResponseDTO responseDTO;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserTest() {
        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);

        assertNotNull(userController.createUser(userDTO));
    }

    @Test
    public void loginTest() {
        when(userService.login("user", "password")).thenReturn(responseDTO);
        assertNotNull(userController.loginUser("user", "password"));
    }





}
