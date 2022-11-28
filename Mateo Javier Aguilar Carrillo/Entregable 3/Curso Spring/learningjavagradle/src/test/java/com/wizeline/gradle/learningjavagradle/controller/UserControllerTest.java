package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.service.UserServiceImpl;
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

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.wizeline.gradle.learningjavagradle.Datos.Datos.*;
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
    UserServiceImpl userService;

    @MockBean
    CommonServices commonServices;

    @Mock
    UserDTO userDTO;

    @Mock
    ResponseDTO responseDTO;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserTest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        LOGGER.info("CreateUser Testing...");
        lenient().when(userService.createUser(USER_001.getUser(), USER_001.getPassword())).thenReturn(responseDTO);

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

        CreaUsuariosThread thread = new CreaUsuariosThread(userDTOList, userService);

        assertAll(
                () -> assertNotNull(thread),
                () -> assertNotNull(userController.createUsers(userDTOList)),
                () -> assertEquals(userController.createUsers(userDTOList).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    public void createUserWithRandomPasswordTest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        LOGGER.info("createUserWithRandomPassword Testing...");
        UserDTO userDTOMock = new UserDTO("user1", "");
        lenient().when(userService.createUser(userDTOMock.getUser())).thenReturn(responseDTO);
        assertNotNull(userController.createUser(userDTOMock));
    }

    @Test
    public void updateUserTest() {
        LOGGER.info("UpdateUser Testing...");
        userService.createUser(USER_001.getUser(), USER_001.getPassword());
        lenient().when(userService.updateUser(USER_001.getUser(), USER_002.getPassword())).thenReturn(responseDTO);
        assertNotNull(userController.updateUser(new UserDTO(USER_001.getUser(), USER_002.getPassword())));
    }

    @Test
    public void deleteUserTest() {
        LOGGER.info("DeleteUser Testing...");
        userService.createUser(USER_001.getUser(), USER_001.getPassword());
        lenient().when(userService.deleteUser(USER_001.getUser())).thenReturn(responseDTO);
        assertNotNull(userController.deleteUser(USER_001.getUser()));
    }
}
