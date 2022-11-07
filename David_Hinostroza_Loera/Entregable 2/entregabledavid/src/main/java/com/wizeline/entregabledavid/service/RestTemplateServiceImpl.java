package com.wizeline.entregabledavid.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.entregabledavid.model.RestAPIDTO;
import com.wizeline.entregabledavid.repository.RestTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RestTemplateServiceImpl implements RestTemplateService{

    @Autowired
    RestTemplateRepository restTemplateRepository;

    private static final Logger LOGGER = Logger.getLogger(RestTemplateServiceImpl.class.getName());

    @Override
    public List<RestAPIDTO> getDatos() {
        ResponseEntity<String> responseAPI = restTemplateRepository.getDatosRestAPI();
        LOGGER.info("Code: "+responseAPI.getStatusCode());
        if (responseAPI.getStatusCodeValue() ==200){
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                List<RestAPIDTO> listaRespuestaAPI = Arrays.asList(objectMapper.readValue(responseAPI.getBody(), RestAPIDTO[].class));
                LOGGER.info("JSON array to List of objects");
                
                return listaRespuestaAPI;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


        }
        return null;
    }
}
