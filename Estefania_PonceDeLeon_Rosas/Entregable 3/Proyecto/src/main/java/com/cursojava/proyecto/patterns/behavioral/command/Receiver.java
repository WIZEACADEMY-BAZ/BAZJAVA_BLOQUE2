package com.cursojava.proyecto.patterns.behavioral.command;

import com.cursojava.proyecto.patterns.behavioral.chainofresponsibility.ChainOfClasificadorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    public void lanzarPokeball() {
        LOGGER.info("Lanzando pokeball...");
    }

    public void esperar() {
        LOGGER.info("Esperando...");
    }

    public void capturar() {
        Random rd = new Random();
        if(rd.nextBoolean())
            LOGGER.info("El pokemon ha sido capturado...");
        else
            LOGGER.info("El pokemon se ha liberado!");
    }
}
