package com.wizeline.entregabledavid.controller;

import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.model.RestAPIDTO;
import com.wizeline.entregabledavid.service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class RestTemplateController {
    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    RestTemplateService restTemplateService;

    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping("/getRestAPI")
    public ResponseEntity<?> getRestAPI(){
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        List<RestAPIDTO> restAPIDTO = restTemplateService.getDatos();
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity<>(restAPIDTO.stream().sorted(Comparator.comparingInt(RestAPIDTO::getUserId).reversed())
                .distinct().collect(Collectors.toList()), responseHeaders, HttpStatus.OK);
    }


}
