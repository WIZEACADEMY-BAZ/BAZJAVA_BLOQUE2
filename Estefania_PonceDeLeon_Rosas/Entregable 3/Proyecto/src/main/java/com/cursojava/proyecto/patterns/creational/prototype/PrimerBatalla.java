package com.cursojava.proyecto.patterns.creational.prototype;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class PrimerBatalla implements Estrategia, Cloneable{

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimerBatalla.class);

    @Override
    public void crearEstrategia() {
        try {
            Object obj = this.clone();
            LOGGER.info("Eligiendo como primer elemento un Pokemon tipo Tierra");
            LOGGER.info("Created battle object with id: " + obj.hashCode());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
