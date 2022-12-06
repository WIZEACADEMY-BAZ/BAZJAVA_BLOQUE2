package com.wizeline.maven.learningjavamaven.service;


import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImplTest.class.getName());

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void createUser() {
        LOGGER.info("Entrando a realizar la prueba de createUser ");
        ResponseDTO responseDTO = userServiceImpl.createUser("eduardo", "123");
        LOGGER.info("responseDTO");
        assertNotNull(responseDTO);
    }

    @Test
    void login() {
        LOGGER.info("Entrando a realizar la pruba de login");
        ResponseDTO responseDTO = userServiceImpl.login("eduardos", "123");
        LOGGER.info("responseDTO" + responseDTO);
        assertNotNull(responseDTO);
        LOGGER.info("Prueba correctamente");
    }
}
