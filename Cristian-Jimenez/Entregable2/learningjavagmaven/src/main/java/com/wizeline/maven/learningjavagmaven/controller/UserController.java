package com.wizeline.maven.learningjavagmaven.controller;

import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wizeline.maven.learningjavagmaven.service.UserService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @GetMapping(value="/login",produces="application/json")
    public ResponseEntity<ResponseModel> login(@RequestParam String user, String password){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        ResponseModel response =new ResponseModel();
        response= userService.login(user,password);
        LOGGER.info("Login - Completado");
        return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
    }

    @PostMapping(value="/createUser", produces="application/json")
    public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel request){
        LOGGER.info("LearningJava -Procesando peticion HTTP de tipo POST -Iniciando");
        ResponseModel response=new ResponseModel();
        response= userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create User - Completado");
        return new ResponseEntity<ResponseModel>(response,HttpStatus.OK);
    }


}
