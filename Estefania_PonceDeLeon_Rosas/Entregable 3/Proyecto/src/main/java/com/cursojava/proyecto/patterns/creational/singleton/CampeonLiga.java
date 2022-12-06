package com.cursojava.proyecto.patterns.creational.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CampeonLiga {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampeonLiga.class);

    private static CampeonLiga instance;
    private String nombre;
    private CampeonLiga(String nombre) {
        this.nombre=nombre;
    }

    public static CampeonLiga getCampeon(String nombre) {
        if (instance == null) {
            instance = new CampeonLiga(nombre);
        }
        return instance;
    }

    public void retar(){
        LOGGER.info("Retando al campeon de la liga: "+ nombre );
    }
}
