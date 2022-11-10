package com.cursojava.proyecto.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;

/**
 * ItemReader for UserJob.
 */
public class EntrenadorReader implements ItemReader<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrenadorReader.class);

    private String[] nombres = {"Phoebe", "Rachel", "Monica", "Chandler", "Ross", "Joey"};

    private int index = 0;

    @Override
    public String read() {
        if (index >= nombres.length) {
            return null;
        }
        String data = index + " " + nombres[index];
        index++;
        LOGGER.info("EntrenadorReader: Reading data: {}", data);
        return data;
    }
}
