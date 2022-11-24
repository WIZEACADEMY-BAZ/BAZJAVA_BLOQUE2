package com.Wizeline.maven.learningjavamaven.controller;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/Throttlin")
public class ControllerThrottlin {

           private final Bucket bucket;

        public ControllerThrottlin() {
            Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
            Bandwidth limit = Bandwidth.classic(5, refill);
            this.bucket = Bucket.builder()
                    .addLimit(limit)
                    .build();
        }


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
