package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.model.RandomPassword;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;
import com.wizeline.gradle.learningjavagradle.utils.CreaUsuariosThread;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @MockBean
    CommonServices commonServices;

    @Mock
    UserDTO userDTO;

    @Mock
    ResponseDTO responseDTO;

    @Mock
    UserRepository repository;



    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserTest() {
        LOGGER.info("CreateUser Testing...");
        lenient().when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);

        assertNotNull(userController.createUser(userDTO));
    }

    @Test
    public void loginTest() {
        LOGGER.info("Login Testing...");
        lenient().when(commonServices.login("user", "password")).thenReturn(responseDTO);
        assertNotNull(userController.loginUser("user", "password"));
    }

    @Test
    public void createUsersTest() {
        LOGGER.info("CreateUsers Testing...");
        List<UserDTO> userDTOList = List.of(new UserDTO("userT1", "passT1"), new UserDTO("userT2", "passT2"));

        CreaUsuariosThread thread = new CreaUsuariosThread(userDTOList, repository);

        assertAll(
                () -> assertNotNull(thread),
                () -> assertNotNull(userController.createUsers(userDTOList)),
                () -> assertEquals(userController.createUsers(userDTOList).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    public void createUserWithRandomPasswordTest() {
        LOGGER.info("createUserWithRandomPassword Testing...");
        UserDTO userDTOMock = new UserDTO("user1", "");
        lenient().when(userService.createUser(userDTOMock.getUser())).thenReturn(responseDTO);
        assertNotNull(userController.createUser(userDTOMock));
    }

    @Test
    public void updateUserTest() {
        LOGGER.info("UpdateUser Testing...");
        lenient().when(userService.updateUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);
        assertNotNull(userController.updateUser(userDTO));
    }

    @Test
    public void deleteUserTest() {
        LOGGER.info("DeleteUser Testing...");
        lenient().when(userService.deleteUser(userDTO.getUser())).thenReturn(responseDTO);
        assertNotNull(userController.deleteUser(userDTO.getUser()));
    }
}
