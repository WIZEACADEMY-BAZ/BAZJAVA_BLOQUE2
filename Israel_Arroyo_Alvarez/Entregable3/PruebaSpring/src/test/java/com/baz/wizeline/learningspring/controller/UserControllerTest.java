package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.model.ResponseDTO;
import com.baz.wizeline.learningspring.model.UserDTO;
import com.baz.wizeline.learningspring.service.UserService;
import com.baz.wizeline.learningspring.utils.CommonServices;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    CommonServices commonServices;

    @InjectMocks
    UserController controller;

    Bucket bucket;
    ResponseDTO responseDTO;
    UserDTO userDTO;
    List<UserDTO> users;

    @BeforeEach
    void setUp() {
        responseDTO = new ResponseDTO();
        userDTO = new UserDTO();
        userDTO.setUser("volcom");
        userDTO.setPassword("Akali89@");
        users = new ArrayList<>();
        users.add(userDTO);
        userDTO = new UserDTO();
        userDTO.setUser("volcom1");
        userDTO.setPassword("Akali88@");
        users.add(userDTO);


        responseDTO.setStatus("200");
        responseDTO.setCode("200");
    }

    @Test
    @DisplayName("Login")
    void loginUser() {
        when(commonServices.login(any(), anyString())).thenReturn(responseDTO);
        ResponseEntity<ResponseDTO> response = controller.loginUser(userDTO.getUser(),userDTO.getPassword());
        assertNotNull(response);
    }

    @Test
    @DisplayName("Crear usuario")
    void createUserAccount(){

        when(userService.createUser(anyString(), anyString())).thenReturn(responseDTO);
        ResponseEntity<ResponseDTO> response = controller.createUserAccount(userDTO);
        assertEquals("200", response.getBody().getStatus());

    }



    @Test
    @DisplayName("Crear muchos usuarios")
    void createUsersAccount(){

        when(userService.createUser(anyString(), anyString())).thenReturn(responseDTO);
        ResponseEntity<List<ResponseDTO>> response = controller.createUsersAccount(users);
        assertTrue(response.getBody().size() > 0);


    }

}