package com.wizeline.learningspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.learningspring.model.herencia.UserBreaches;
import com.wizeline.learningspring.service.UserBreachesService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@Tag(name = "Banking Account",
        description = "Contiene operaciones comunes realizadas en las cuentas.")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class UserBreachesController {
    @Autowired
    UserBreachesService userBreachesService;
    private final Bucket bucket;

    public UserBreachesController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/userBreaches")
    public ResponseEntity<List<UserBreaches>> getUserBreaches() throws JsonProcessingException {
        if (bucket.tryConsume(1)) {
            List<UserBreaches> userBreaches = userBreachesService.breaches();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<>(userBreaches, responseHeaders, HttpStatus.OK);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
