package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import com.wizeline.maven.learningjavamaven.model.UserModel;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CommonServices commonServices;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private static String msgProcPeticion = "LearningJavaMaven - Inicia procesamiento de peticion ...";
    private static String path = "http://localhost:8080/api";
    private Instant inicio;

    @GetMapping(value="/login", produces="application/json")
    public ResponseEntity<ResponseModel> login(@RequestParam String user, @RequestParam String password){
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();
        ResponseModel responseModel;

        LOGGER.info("LearningJavaMaven - Procesando peticion HTTP de tipo GET");
        UserModel userModel = new UserModel();
        StringBuilder stringBuilder = new StringBuilder(path + "/login");
        stringBuilder.append("?user=" + user);
        stringBuilder.append("&password=" + password);
        URI uri = URI.create(stringBuilder.toString());

        userModel = userModel.getParameters(splitQuery(uri));
        responseModel = commonServices.login(userModel.getUser(), userModel.getPassword());

        LOGGER.info("Login - Completed");
        return getResponseModelResponseEntity(responseModel, inicio);
    }

    @PostMapping(value="/createUser", produces="application/json")
    public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel request) throws JsonProcessingException {
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();
        ResponseModel responseModel;

        responseModel =  createUser(request.getUser(), request.getPassword());

        LOGGER.info("Create User - Completed");
        return getResponseModelResponseEntity(responseModel, inicio);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseModel>> createUsersAccount(@RequestBody List<UserModel> userDTOList) {
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();
        AtomicReference<ResponseModel> response = new AtomicReference<>(new ResponseModel());
        List<ResponseModel> responseList = new ArrayList<>();

        userDTOList.stream().forEach( userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        LOGGER.info("Create Users - Completed");
        return getResponseModelResponseEntity(responseList, inicio);
    }

    @NotNull
    private ResponseEntity<ResponseModel> getResponseModelResponseEntity(ResponseModel responseModel, Instant inicioDeEjecucion) {

        String total = String.valueOf(Duration.between(inicioDeEjecucion, Instant.now()).toMillis()).concat(" milisegundos");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<>(responseModel, responseHeaders, HttpStatus.OK);
    }

    @NotNull
    private ResponseEntity<List<ResponseModel>> getResponseModelResponseEntity(List<ResponseModel> responseList, Instant inicioDeEjecucion) {

        String total = String.valueOf(Duration.between(inicioDeEjecucion, Instant.now()).toMillis()).concat(" milisegundos");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<>(responseList, responseHeaders, HttpStatus.OK);
    }

    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

    private ResponseModel createUser(String user, String password) {
        return userService.createUser(user, password);
    }
}
