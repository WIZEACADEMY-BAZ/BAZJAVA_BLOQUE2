package com.wizeline.maven.learninjavamaven.UserServiceImplTest;

import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplTest.class);
    @InjectMocks
    private UserServiceImpl serviceTest;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    void createUserTest() {
        LOGGER.info("createUserTest");
        UserDTO userDTO = new UserDTO("userTest", "passwordTest");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(userDTO);
        ResponseDTO response = serviceTest.createUser("userTest", "passwordTest");
        assertNotNull(response);
    }

    @Test
    void createUserNotExistsTest() {
        LOGGER.info("createUserNotExistsTest");
        UserDTO userDTO = new UserDTO("user", "pass");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(null);
        lenient().when(mongoTemplate.save(any())).thenReturn(userDTO);
        ResponseDTO response = serviceTest.createUser("user", "pass");
        assertNotNull(response);
    }

    @Test
    void loginTest() {
        LOGGER.info("loginTest");
        UserDTO userDTO = new UserDTO("user", "pass");
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(userDTO);
        ResponseDTO response = serviceTest.login("user", "pass");
        assertNotNull(response);
    }

    @Test
    void loginNotPassTest() {
        LOGGER.info("loginNotPassTest");
        ResponseDTO response = serviceTest.login("userTest", null);
        assertNotNull(response);
    }

}
