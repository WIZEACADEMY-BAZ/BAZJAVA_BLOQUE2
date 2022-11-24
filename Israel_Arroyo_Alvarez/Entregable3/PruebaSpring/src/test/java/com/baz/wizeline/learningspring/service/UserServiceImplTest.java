package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.ResponseDTO;
import com.baz.wizeline.learningspring.repository.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserServiceImpl userService;
    String user;
    String password;


    @BeforeEach
    void setUp() {
        user = "volcom";
        password = "Akali87@";
    }

    @Test
    void createUser() {
        ResponseDTO resp = new ResponseDTO();
        when(userDAO.createUser(any(), any())).thenReturn("success");
        resp = userService.createUser(user,password);
        assertEquals("OK000", resp.getCode());

    }

    @Test
    void createUserWithUserInvalid() {
        ResponseDTO resp = new ResponseDTO();
        resp = userService.createUser(null,password);
        assertEquals("OK001", resp.getCode());

    }

    @Test
    void createUserPassInvalid() {
        ResponseDTO resp = new ResponseDTO();
        resp = userService.createUser(user,null);
        assertEquals("OK001", resp.getCode());

    }

    @Test
    void loginCorrect(){
        ResponseDTO resp = new ResponseDTO();
        when(userDAO.login(any(),any())).thenReturn("success");
        resp = userService.login(user,password);
        assertEquals("OK000", resp.getCode());
    }

    @Test
    void loginIncorrectUser(){
        ResponseDTO resp = new ResponseDTO();
        resp = userService.login(null,password);
        assertEquals("ER001", resp.getCode());
    }

    @Test
    void loginIncorrectPass(){
        ResponseDTO resp = new ResponseDTO();
        resp = userService.login(user,null);
        assertEquals("ER001", resp.getCode());
    }


}