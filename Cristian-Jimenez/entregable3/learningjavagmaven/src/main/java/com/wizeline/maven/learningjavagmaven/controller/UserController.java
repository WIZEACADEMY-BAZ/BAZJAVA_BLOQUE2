package com.wizeline.maven.learningjavagmaven.controller;

import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.model.UserModel;
import com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wizeline.maven.learningjavagmaven.service.UserService;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Bucket bucket;

    public UserController(){
        Refill refill= Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit =Bandwidth.classic(5, refill);
        this.bucket=Bucket.builder()
                .addLimit(limit)
                .build();
    }
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

// Throttling

    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        if (bucket.tryConsume(1)) {
            //Aqui va la logica para obtener la informacion
            //Se regresa la respuesta normalmente
            LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
            ResponseModel response1 = new ResponseModel();
            response1 = userService.login("user1@wizeline.com", "pass1");
            LOGGER.info("Login - Completado");
        }
        //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
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
