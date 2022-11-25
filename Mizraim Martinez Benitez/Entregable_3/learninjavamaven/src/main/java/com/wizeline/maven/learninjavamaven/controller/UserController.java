package com.wizeline.maven.learninjavamaven.controller;

import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController extends Thread{

    private final Bucket bucket;


    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }


    public UserController(Bucket bucket) {
        this.bucket = bucket;
    }


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


    //Patron de diseño "Throttling"
    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        if (bucket.tryConsume(1)) {
            //Aqui va la logica para obtener la informacion
            //Se regresa la respuesta normalmente
            return ResponseEntity.ok("It's ok");
        }

        //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

}
