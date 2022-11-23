package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.AnswerAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class RestTemplateServiceImplTest {

    @Mock
    RestTemplate utilRestTemplate;

    @InjectMocks
    RestTemplateServiceImpl restTemplateService;

    AnswerAPIDTO answerAPIDTO;

    String url;
    ResponseEntity<String> respuesta;

    @BeforeEach
    void setUp() {
        url = "https://yesno.wtf/api";
        answerAPIDTO= new AnswerAPIDTO();
        answerAPIDTO.setImage(url);
        String body = "{\"image\":\"https://yesno.wtf/assets/yes/9-6403270cf95723ae4664274db51f1fd4.gif\"}";
        respuesta = new ResponseEntity<>(body, HttpStatus.OK);

    }

    @Test
    void exampleRest() throws JsonProcessingException {
        when(utilRestTemplate.exchange(url,HttpMethod.GET,null,String.class)).thenReturn(respuesta);
        AnswerAPIDTO answer = restTemplateService.exampleRest();
        assertNotEquals(answer.getImage(),answerAPIDTO.getImage());

    }
}