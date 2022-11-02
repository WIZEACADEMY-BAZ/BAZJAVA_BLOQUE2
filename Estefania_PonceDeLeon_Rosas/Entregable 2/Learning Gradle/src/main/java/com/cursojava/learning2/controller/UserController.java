package com.cursojava.learning2.controller;

import com.cursojava.learning2.model.ErrorDTO;
import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.model.UserDTO;
import com.cursojava.learning2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api")
public class UserController {
    @Autowired
    UserService userService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final String  logMsg= "LearningJava - Inicia procesamiento de peticion ";
    @GetMapping(value = "login",produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password) {
        LOGGER.info(logMsg+"LOGIN");
        ResponseDTO response = this.userService.login(user, password);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "createUser")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO user){
        LOGGER.info(logMsg+" CREATE USER");
        ResponseDTO response = this.userService.crearUsuario(user);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "createUsers")
    public ResponseEntity<ResponseDTO> createUsers(@RequestBody List<UserDTO> users){
        LOGGER.info(logMsg+" CREATE MULTIPLE USER");
        ResponseDTO response = this.userService.crearUsuarios(users);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

}
