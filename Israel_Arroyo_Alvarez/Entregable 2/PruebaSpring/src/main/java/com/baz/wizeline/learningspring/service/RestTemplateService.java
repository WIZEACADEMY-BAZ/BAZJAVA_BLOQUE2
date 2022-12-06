package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.AnswerAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface RestTemplateService {

     AnswerAPIDTO exampleRest() throws JsonProcessingException;
}
