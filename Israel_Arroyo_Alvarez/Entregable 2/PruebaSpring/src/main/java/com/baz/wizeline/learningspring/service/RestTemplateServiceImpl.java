package com.baz.wizeline.learningspring.service;


import com.baz.wizeline.learningspring.model.AnswerAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RestTemplateServiceImpl implements RestTemplateService{

    RestTemplate utilRestTemplate = new RestTemplate();




    @Override
    public AnswerAPIDTO exampleRest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        ResponseEntity<String> respuesta = utilRestTemplate.exchange("https://yesno.wtf/api", HttpMethod.GET, null, String.class);
        return mapper.readValue(respuesta.getBody(), AnswerAPIDTO.class);

    }
}
