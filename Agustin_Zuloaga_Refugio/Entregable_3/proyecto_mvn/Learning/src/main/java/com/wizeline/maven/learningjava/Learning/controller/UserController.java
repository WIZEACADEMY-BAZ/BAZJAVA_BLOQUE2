package com.wizeline.maven.learningjava.Learning.controller;

import java.util.logging.Logger;

import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import com.wizeline.maven.learningjava.Learning.model.UserDTO;
import com.wizeline.maven.learningjava.Learning.model.UserDateDTO;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wizeline.maven.learningjava.Learning.service.UserService;
import java.util.List;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    private final Bucket bucket;

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok("It's ok");
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

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
    @GetMapping("/userDate")
    public ResponseEntity<List<UserDateDTO>> userDate(@RequestParam String userId) {
        LOGGER.info(msgProcPeticion);

        List<UserDateDTO> response = userService.getUserDateIterator(userId);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

}