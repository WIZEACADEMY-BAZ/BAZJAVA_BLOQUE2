package com.wizeline.maven.learninjavamaven.controller;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.service.UserService;
import com.wizeline.maven.learninjavamaven.utils.CommonServices;
import io.github.bucket4j.Bucket;
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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    @MockBean
    private UserService userService;
    @MockBean
    CommonServices commonServices;
    @Mock
    private ResponseDTO responseDTO;
    @Autowired
    private UserController userController;

    @Mock
    private Bucket bucket;

    @Test
    void loginUser() {
        LOGGER.info("LoginUserTest");
        when(commonServices.login("user", "password")).thenReturn(responseDTO);
        assertAll(
                () -> assertNotNull(userController.login("user", "password")),
                () -> assertEquals(userController.login("user", "password").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void createUserAccount() {
        LOGGER.info("CreateUserTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");

        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);
        assertAll(
                () -> assertEquals(userController.createUser(userDTO).getBody(), responseDTO),
                () -> assertEquals(userController.createUser(userDTO).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getUsersTest(){
        LOGGER.info("getUsersTest");
        ResponseEntity<String> responseEntity = userController.getUsers();

        assertNotNull(responseEntity);
    }

}
