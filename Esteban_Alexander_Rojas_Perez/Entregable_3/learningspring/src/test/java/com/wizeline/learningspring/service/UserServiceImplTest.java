package com.wizeline.learningspring.service;

import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);
    @InjectMocks
    UserServiceImpl service;
    @Mock
    MongoTemplate mongoTemplate;

    @Test
    void createUser() {
        LOGGER.info("createUserTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(userDTO);
        ResponseDTO response = service.createUser("userTest", "passwordTest");
        assertNotNull(response);
    }

    @Test
    void createUserNotExist() {
        LOGGER.info("createUserNotExistTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(null);
        lenient().when(mongoTemplate.save(any())).thenReturn(userDTO);
        ResponseDTO response = service.createUser("userTest", "passwordTest");
        assertNotNull(response);
    }

    @Test
    void createUserNotUser() {
        LOGGER.info("createUserNotUserTest");
        ResponseDTO response = service.createUser(null, "passwordTest");
        assertNotNull(response);
    }

    @Test
    void createUserNotPass() {
        LOGGER.info("createUserNotPassTest");
        ResponseDTO response = service.createUser("userTest", null);
        assertNotNull(response);
    }

    @Test
    void login() {
        LOGGER.info("loginTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(userDTO);
        ResponseDTO response = service.createUser("userTest", "passwordTest");
        assertNotNull(response);
    }

    @Test
    void loginUserNull() {
        LOGGER.info("loginUserNullTest");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(null);
        ResponseDTO response = service.createUser("userTest", "passwordTest");
        assertNotNull(response);
    }

    @Test
    void loginNotUser() {
        LOGGER.info("loginNotUserTest");
        ResponseDTO response = service.login(null, "passwordTest");
        assertNotNull(response);
    }

    @Test
    void loginNotPass() {
        LOGGER.info("loginNotPassTest");
        ResponseDTO response = service.login("userTest", null);
        assertNotNull(response);
    }
}