package com.wizeline.learningspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.learningspring.model.herencia.UserBreaches;
import com.wizeline.learningspring.model.herencia.UserBreachesDetails;
import com.wizeline.learningspring.service.UserBreachesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserBreachesControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBreachesControllerTest.class);
    @MockBean
    UserBreachesService service;
    @Autowired
    UserBreachesController controller;
    @Test
    void getUserBreaches() throws JsonProcessingException {
        LOGGER.info("getUserBreachesTest");
        List<String> dataClasses = new ArrayList<>();
        dataClasses.add("Email addresses");
        dataClasses.add("IP addresses");
        dataClasses.add("Names");
        dataClasses.add("Passwords");
        List<UserBreaches> user = List.of(
                new UserBreaches("000webhost", "000webhost", "000webhost.com", "2015-03-01", "2015-10-26T23:35:45Z","2017-12-10T21:44:27Z"),
                new UserBreaches("123RF", "123RF", "123rf.com", "2020-03-22", "2020-11-15T00:59:50Z", "2020-11-15T01:07:10Z")
        );

        when(service.breaches()).thenReturn(user);

        assertAll(
                () -> assertNotNull(controller.getUserBreaches()),
                () -> assertEquals(controller.getUserBreaches().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }
}