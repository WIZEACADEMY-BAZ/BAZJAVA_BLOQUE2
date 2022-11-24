package com.cursojava.proyecto.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Processor for UserJob.
 */
public class EntrenadorProcessor implements ItemProcessor<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntrenadorProcessor.class);

    @Override
    public String process(String data) throws Exception {
        LOGGER.info("EntrenadorProcessor: Processing data: {}", data);
        data = data.toUpperCase();
        return data;
    }
}
