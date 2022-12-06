package com.wizeline.maven.learningjava.Learning.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.learningjava.Learning.model.detalle.UserDescription;
import com.wizeline.maven.learningjava.Learning.utils.exceptions.ExcepcionUnica;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsultaUsuarioServiceImpl implements ConsultaUsuarioService {

    RestTemplate restTemplate = new RestTemplate();

    public List<UserDescription> consultaSuccess() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        try {
            return mapper.readValue(peticion(), new TypeReference<List<UserDescription>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ExcepcionUnica(403, Collections.singletonList(e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    public List<UserDescription> consultaError() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.setTimeZone(TimeZone.getTimeZone("Mexico/General"));
        try {
            return mapper.readValue(peticion(), new TypeReference<List<UserDescription>>() {
            });
        } catch (JsonProcessingException e) {
            throw new ExcepcionUnica(403, Collections.singletonList("Error desencriptado"), HttpStatus.CONFLICT);
        }
    }

    private String peticion() {
        ResponseEntity<String> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/users", HttpMethod.GET, null, String.class);
        return response.getBody();
    }

    public List<UserDescription> filtrado() {
        List<UserDescription> userList = consultaSuccess();
        ValidarCodigoSimple validarCodigoSimple = new ValidarCodigoSimple();
        return userList.stream().map(user -> {
            if (validarCodigoSimple.validarCodigo(user.getAddress())) {
                List<String> randomEnvio = Arrays.asList("SMS", "EMAIL", "PUSH", "WHATS");
                Instant inicioDeEjecucion = Instant.now();
                NotificacionThread notificacionThread = new NotificacionThread(user, inicioDeEjecucion, getRandomElement(randomEnvio));
                notificacionThread.start();
                return user;
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private String getRandomElement(List<String> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
