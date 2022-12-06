package com.cursojava.proyecto.controllers;

import com.cursojava.proyecto.services.ActuatorService;
import com.fasterxml.jackson.core.JsonEncoding;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("actuactorendpoints")
public class ActuatorController {

    @Autowired
    ActuatorService actuatorService;

    @GetMapping(value ="info" , produces = "application/json")
    public JsonObject getInfo(){
        return this.actuatorService.getInfo();
    }

    @GetMapping(value ="metrics" , produces = "application/json")
    JsonObject getMetrics(){
        return this.actuatorService.getMetrics();
    }

    @GetMapping(value ="mappings" , produces = "application/json")
    JsonObject getMappings(){
        return this.actuatorService.getMappings();
    }

    @GetMapping(value = "loggers",  produces = "application/json")
    JsonObject getLoggers(){
        return this.actuatorService.getLoggers();
    }
}
