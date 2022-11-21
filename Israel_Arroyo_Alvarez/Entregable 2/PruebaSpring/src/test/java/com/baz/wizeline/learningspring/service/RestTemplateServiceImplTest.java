package com.baz.wizeline.learningspring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@ExtendWith(MockitoExtension.class)
class RestTemplateServiceImplTest {

    @Mock
    RestTemplate utilRestTemplate = new RestTemplate();

    @InjectMocks
    RestTemplateServiceImpl restTemplateService;


    ResponseEntity<String> respuesta = new ResponseEntity<>("{\n \"image\": \"https://yesno.wtf/assets/no/6-4bf0a784c173f70a0cab96efd9ff80c9.gif\"\n }", HttpStatus.OK);




    @Test
    void exampleRest() {

        Mockito.when(utilRestTemplate.exchange("", HttpMethod.GET,null,String.class)).thenReturn(respuesta);




    }
}