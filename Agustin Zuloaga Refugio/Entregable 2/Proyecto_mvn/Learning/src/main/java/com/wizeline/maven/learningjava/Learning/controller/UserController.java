package com.wizeline.maven.learningjava.Learning.controller;

import java.util.logging.Logger;

import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import com.wizeline.maven.learningjava.Learning.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wizeline.maven.learningjava.Learning.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password) {
        LOGGER.info("Learning - Prosesando - Peticion HTTP de tipo GET Startting...");
        ResponseDTO response = new ResponseDTO();

        response = userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request) {
        LOGGER.info("Learning - Prosesando - Peticion HTTP de tipo POST Startting...***");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

}

}