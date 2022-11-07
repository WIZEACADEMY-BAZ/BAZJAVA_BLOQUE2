package com.wizeline.entregabledavid.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class RestTemplateRepositoryImpl implements RestTemplateRepository{
    @Override
    public ResponseEntity<String> getDatosRestAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String restAPIUrl
                = "https://jsonplaceholder.typicode.com/posts";
        ResponseEntity<String> response
                = restTemplate.getForEntity(restAPIUrl, String.class);

        return response;
    }
}
