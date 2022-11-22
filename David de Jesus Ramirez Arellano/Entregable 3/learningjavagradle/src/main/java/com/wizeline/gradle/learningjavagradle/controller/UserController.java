package com.wizeline.gradle.learningjavagradle.controller;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.NotificationsException;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping(value= "/login/{user}/{password}", produces = "application/json")
    public ResponseEntity<ResponseDTO> loginUser(@PathVariable String user, @PathVariable String password){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        ResponseDTO response = new ResponseDTO();
        response= userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value ="/createUser", produces="application/json", consumes="application/json")
    public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO request) {
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - Completed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
