package com.wizeline.baz.LearningSpring.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.baz.LearningSpring.service.KafkaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "Kafka Controller", description = "Servicio que inserta y consume kafka.")
@RequestMapping("/api2")
@RestController
public class KafkaController {

    @Autowired
    KafkaService service;

    @ResponseStatus(OK)
    @GetMapping("/kafkaProducer")
    public ResponseEntity<String> kafkaProducer(@RequestParam String input) {
        return new ResponseEntity<>(service.producerKafka(input).toString(), HttpStatus.OK);
    }

}
