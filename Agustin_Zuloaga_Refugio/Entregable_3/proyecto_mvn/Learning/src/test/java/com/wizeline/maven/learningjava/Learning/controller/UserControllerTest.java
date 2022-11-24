package com.wizeline.maven.learningjava.Learning.controller;

import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;

    ResponseDTO responseDTO = new ResponseDTO();

    @BeforeEach
    void before() {
        responseDTO.setStatus("correcto");
        responseDTO.setCode("OK000");
    }

    @Test
    @DisplayName("Procesos de consumo de usuarios")
    void getUsers() {
        ResponseEntity<String> response = userController.getUsers();
        assertNotNull(response);
    }

}