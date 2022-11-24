package com.cursojava.proyecto.batch;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class EntrenadorItemProcessor implements ItemProcessor<EntrenadorDTO, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrenadorItemProcessor.class);

    @Override
    public String process(EntrenadorDTO entrenadorDTO) {
        String miembros = "Nombre: " + entrenadorDTO.getNombre() + " Rango: " + entrenadorDTO.getRango();
        LOGGER.info("converting '{}' into '{}'", entrenadorDTO, miembros);
        return miembros;
    }
}
