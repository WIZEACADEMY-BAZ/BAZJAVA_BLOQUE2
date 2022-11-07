package com.wizeline.learningspring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.learningspring.model.herencia.UserBreaches;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserBreachesServiceImpl implements UserBreachesService{
    RestTemplate restTemplate = new RestTemplate();
    private String urlAPI = "https://haveibeenpwned.com/api/v2/breaches";

    @Override
    public List<UserBreaches> breaches() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        ResponseEntity<String> response = restTemplate.exchange(urlAPI, HttpMethod.GET, null, String.class);

        return mapper.readValue(response.getBody(), new TypeReference<List<UserBreaches>>() {
        });
    }
}
