package com.cursojava.proyecto.patterns.creational.factoryMethod.contrincantes;

import com.cursojava.proyecto.patterns.creational.factoryMethod.Contrincante;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContrincanteEstiloFuego implements Contrincante {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContrincanteEstiloFuego.class);

    @Override
    public void verTipoBase() {
        LOGGER.info("Te ha tocado luchar contra pokemons tipo Fuego");
    }
}
