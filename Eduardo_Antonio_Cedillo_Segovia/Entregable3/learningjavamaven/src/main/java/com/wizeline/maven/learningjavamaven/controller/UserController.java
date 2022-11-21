package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.Circulo;
import com.wizeline.maven.learningjavamaven.model.FabricaFormas;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.repository.Forma;
import com.wizeline.maven.learningjavamaven.service.UserService;
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
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = Logger.getLogger((UserController.class.getName()));

    private final Bucket bucket;

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password) {
        LOGGER.info(("LearningJava - Procesando peticion HTTP de tipo GET - Starting..."));
        ResponseDTO response = new ResponseDTO();
        response = userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request) {
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<String> getUsers() {
        if (bucket.tryConsume(1)) {
            //Aqui va la logica para obtener la informacion
            //Se regresa la respuesta normalmente
            return ResponseEntity.ok("It's ok");
        }

        //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }



    @GetMapping(value = "/formas", produces = "application/json")
    public ResponseEntity<String> getFormas() {
        FabricaFormas obj = new FabricaFormas();

        Forma obj1 = obj.getShape("CIRCULO");
        obj1.dibujar();

        Forma obj2 = obj.getShape("CUADRADO");
        obj2.dibujar();

        Forma obj3 = obj.getShape("RECTANGULO");
        obj3.dibujar();

        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
