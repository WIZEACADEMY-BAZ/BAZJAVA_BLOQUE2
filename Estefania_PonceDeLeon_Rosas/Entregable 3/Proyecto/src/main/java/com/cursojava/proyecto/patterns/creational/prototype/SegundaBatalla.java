package com.cursojava.proyecto.patterns.creational.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SegundaBatalla implements Estrategia, Cloneable{
    private static final Logger LOGGER = LoggerFactory.getLogger(SegundaBatalla.class);

    @Override
    public void crearEstrategia() {
        try {
            Object obj = this.clone();
            LOGGER.info("Eligiendo como primer elemento un Pokemon tipo Fuego");
            LOGGER.info("Created battle object with id: " + obj.hashCode());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
