package com.cursojava.proyecto.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
public class EntrenadorWriter implements ItemWriter<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrenadorWriter.class);

    @Override
    public void write(List<? extends String> list) {
        for (String data: list) {
            LOGGER.info("EntrenadorWriter: Writing data: " + data);
        }
        LOGGER.info("EntrenadorWriter: Writing data completed!!");
    }
}
