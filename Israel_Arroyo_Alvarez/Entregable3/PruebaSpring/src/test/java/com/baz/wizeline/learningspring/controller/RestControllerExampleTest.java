package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.model.AnswerAPIDTO;
import com.baz.wizeline.learningspring.model.XmlBean;
import com.baz.wizeline.learningspring.service.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestControllerExampleTest {

    @Mock
    RestTemplateService service;

    @InjectMocks
    RestControllerExample controller;

    AnswerAPIDTO answer;

    @BeforeEach
    void setUp() {
        answer = new AnswerAPIDTO();
        answer.setImage("https://yesno.wtf/api");
    }

    @Test
    @DisplayName("RestController Example")
    void testRestTemplate() throws JsonProcessingException {
        when(service.exampleRest()).thenReturn(answer);
        AnswerAPIDTO response = controller.testRestTemplate();
        assertNotNull(response);


    }
}