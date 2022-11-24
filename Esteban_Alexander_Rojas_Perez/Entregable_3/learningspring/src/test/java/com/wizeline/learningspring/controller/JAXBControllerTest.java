package com.wizeline.learningspring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class JAXBControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JAXBControllerTest.class);
    @InjectMocks
    JAXBController controller;

    @Test
    void loginUser() {
        LOGGER.info("loginUserTest");
        assertAll(
                () -> assertNotNull(controller.loginUser()),
                () -> assertEquals(controller.loginUser().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }
}