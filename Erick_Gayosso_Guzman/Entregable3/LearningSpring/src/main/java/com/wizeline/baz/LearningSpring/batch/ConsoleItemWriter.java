package com.wizeline.baz.LearningSpring.batch;

import com.wizeline.baz.LearningSpring.model.NotificacionesDTO;
import com.wizeline.baz.LearningSpring.service.BankAccountServiceImpl;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.logging.Logger;

public class ConsoleItemWriter<T> implements ItemWriter {

    private static final Logger LOGGER = Logger.getLogger(ConsoleItemWriter.class.getName());


    @Override
    public void write(List items) throws Exception {
        items.parallelStream().forEach(t -> {
            LOGGER.info(t.toString());
        });
    }

    @Bean
    public ConsoleItemWriter<NotificacionesDTO> writer()
    {
        return new ConsoleItemWriter<NotificacionesDTO>();
    }
}