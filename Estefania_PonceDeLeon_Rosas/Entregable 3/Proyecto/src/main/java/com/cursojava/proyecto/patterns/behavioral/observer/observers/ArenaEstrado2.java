package com.cursojava.proyecto.patterns.behavioral.observer.observers;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArenaEstrado2 implements Observer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArenaEstrado2.class);
    @Override
    public void update(EntrenadorDTO entrenadorDTO) {
        LOGGER.info("Estrado 2 - "+entrenadorDTO.getNombre() + " está dentro de la competencia...");
    }

    @Override
    public void retire(EntrenadorDTO entrenadorDTO) {
        LOGGER.info("Estrado 2 - "+entrenadorDTO.getNombre() + " se retiro de la competencia...");
    }

    @Override
    public void anunciar(EntrenadorDTO entrenadorDTO) {
        LOGGER.info("Estrado 2 - "+entrenadorDTO.getNombre() + " ha ganado la competencia...");
    }
}
