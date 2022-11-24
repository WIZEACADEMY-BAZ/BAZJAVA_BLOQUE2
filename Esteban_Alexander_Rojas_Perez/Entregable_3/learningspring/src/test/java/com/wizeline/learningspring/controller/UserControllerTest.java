package com.wizeline.learningspring.controller;

import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.model.UserDTO;
import com.wizeline.learningspring.service.UserService;
import com.wizeline.learningspring.utils.CommonServices;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerTest.class);
    @MockBean
    private UserService userService;
    @MockBean
    CommonServices commonServices;
    @Mock
    private ResponseDTO responseDTO;
    @Autowired
    private UserController controller;

    @Test
    void loginUser() {
        LOGGER.info("LoginUserTest");
        when(commonServices.login("user", "password")).thenReturn(responseDTO);
        assertAll(
                () -> assertNotNull(controller.loginUser("user", "password")),
                () -> assertEquals(controller.loginUser("user", "password").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void createUserAccount() {
        LOGGER.info("CreateUserTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");

        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);
        assertAll(
                () -> assertEquals(controller.createUserAccount(userDTO).getBody(), responseDTO),
                () -> assertEquals(controller.createUserAccount(userDTO).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void createUsersAccount() {
        LOGGER.info("CreateUsersAccountTest");
        List<UserDTO> userDTOList = List.of(new UserDTO("userTest1", "passwordTest1"),
                new UserDTO("userTest2", "passwordTest2"));

        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach(userDTO -> {
            String user = userDTO.getUser();
            String password = userDTO.getPassword();
            response.set(controller.createUser(user, password));
            responseList.add(response.get());
        });

        assertAll(
                () -> assertNotNull(controller.createUsersAccount(userDTOList)),
                () -> assertEquals(controller.createUsersAccount(userDTOList).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }
}