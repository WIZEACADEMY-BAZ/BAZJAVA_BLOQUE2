package com.wizeline.learningspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.learningspring.model.herencia.UserBreaches;
import com.wizeline.learningspring.service.UserBreachesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Banking Account",
        description = "Contiene operaciones comunes realizadas en las cuentas.")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class UserBreachesController {
    @Autowired
    UserBreachesService userBreachesService;

    @GetMapping("/userBreaches")
    public ResponseEntity<List<UserBreaches>> getUserBreaches() throws JsonProcessingException {
        List<UserBreaches> userBreaches = userBreachesService.breaches();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<>(userBreaches, responseHeaders, HttpStatus.OK);
    }
}
