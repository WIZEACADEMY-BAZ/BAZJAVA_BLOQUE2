package com.cursojava.proyecto.services;

import org.bson.json.JsonObject;

public interface ActuatorService {

    JsonObject getInfo();
    JsonObject getMetrics();
    JsonObject getMappings();
    JsonObject getLoggers();
}
