package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void createUser() {
        ResponseDTO responseDTO = userServiceImpl.createUser("oswaldo", "123");
        assertNotNull(responseDTO);
    }

    @Test
    void login() {
        ResponseDTO responseDTO = userServiceImpl.login("panfilo", "123");
        assertNotNull(responseDTO);
    }
}