package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.model.AnswerAPIDTO;
import com.baz.wizeline.learningspring.service.RestTemplateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestControllerExample {


    @Autowired
    RestTemplateService restTemplateService;


    @GetMapping("/resttemplate")
    public AnswerAPIDTO testRestTemplate() throws JsonProcessingException {
        AnswerAPIDTO respuesta = restTemplateService.exampleRest();






        return respuesta;
    }

}
