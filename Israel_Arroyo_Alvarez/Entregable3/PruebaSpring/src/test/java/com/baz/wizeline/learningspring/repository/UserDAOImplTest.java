package com.baz.wizeline.learningspring.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDAOImplTest {

    @Mock
    UserDAO dao;

    @InjectMocks
    UserDAOImpl userDAO;

    String user;
    String password;

    @BeforeEach
    void setUp() {
        user = "volcom";
        password = "Akali89@";

    }

    @Test
    void createUser() {
        String resp = userDAO.createUser("doom","Akali23@");
        assertNotNull(resp);
    }

    @Test
    void loginwithExist() {

        String resp = userDAO.login("doom", "Akali23@");
        assertEquals("success", resp);
    }

    @Test
    void loginwithoutExist() {
        String resp = userDAO.login("doomiiiiii", "Akali23@");
        assertEquals("Usuario o password incorrecto", resp);

    }
}