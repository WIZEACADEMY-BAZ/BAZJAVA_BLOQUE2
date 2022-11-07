package com.wizeline.maven.learninjavamaven.controller;

import com.wizeline.maven.learninjavamaven.LearninjavamavenApplication;
import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController extends Thread{

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
        LOGGER.info("LearningJava - Inicia procesamiento de peticion ...");
        ResponseDTO response = new ResponseDTO();
        response = userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request){
        LOGGER.info("LearningJava Procesamiento peticion HTTP tipo POST");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("CreateUser - Completed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getUserAccount", produces = "application/json")
    public ResponseEntity<ResponseDTO> getUserAccount (){
        return null;
    }

}
