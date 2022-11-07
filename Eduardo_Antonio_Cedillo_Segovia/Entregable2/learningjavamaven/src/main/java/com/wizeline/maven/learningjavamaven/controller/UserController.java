package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

@Autowired
    UserService userService;

    private static final Logger LOGGER = Logger.getLogger((UserController.class.getName()));

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
        LOGGER.info(("LearningJava - Procesando peticion HTTP de tipo GET - Starting..."));
        ResponseDTO response = new ResponseDTO();
        response = userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
    @PostMapping(value="/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO>createUser(@RequestBody UserDTO request){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
}
