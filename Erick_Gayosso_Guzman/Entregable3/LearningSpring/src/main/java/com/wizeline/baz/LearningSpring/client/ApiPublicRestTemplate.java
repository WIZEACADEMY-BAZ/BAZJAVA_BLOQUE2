package com.wizeline.baz.LearningSpring.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class ApiPublicRestTemplate {


    public ResponseEntity<String> apiPublic() {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = getRestTemplate(5000);
        final String baseUrl = "https://pokeapi.co/api/v2/pokemon/ditto";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);
        long inicio = 0l;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
                    String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private RestTemplate getRestTemplate(int timeOut) {
        long inicio = System.currentTimeMillis();
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(timeOut);
        simpleClientHttpRequestFactory.setReadTimeout(timeOut);
        return new RestTemplate(simpleClientHttpRequestFactory);
    }

}
