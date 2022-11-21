package com.wizeline.maven.learningjavamaven.controller;


import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    CommonServices commonServices;
    @InjectMocks
    UserController userController;

    @Mock
    private HttpSession session;

    @Mock
    UserDTO userDTO;

    public static final String USER = "Admin";
    public static final String CODIGO_EXITO = "OK";
    private static final String RESPUESTA = "respuesta";


    @Test
    public void loginTest() {
        String user = "usuario";
        String password = "123456";
        ResponseDTO response = new ResponseDTO();
        when(commonServices.login(user, password)).thenReturn(response);
        assertEquals(userController.getUsers().getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void createUserTest() {
        when(session.getAttribute(anyString())).thenReturn(RESPUESTA);
        //   Mockito.when(userDTO.getUser()).thenReturn("");
        assertEquals("usuario", userDTO.getUser());
        assertEquals("password", userDTO.getPassword());

    }
}


