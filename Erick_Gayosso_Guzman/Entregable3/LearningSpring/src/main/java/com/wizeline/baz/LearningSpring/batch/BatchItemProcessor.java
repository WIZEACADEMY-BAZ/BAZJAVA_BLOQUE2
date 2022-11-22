package com.wizeline.baz.LearningSpring.batch;

import com.wizeline.baz.LearningSpring.model.NotificacionesDTO;
import org.springframework.batch.item.ItemProcessor;

import java.util.logging.Logger;

public class BatchItemProcessor implements ItemProcessor<NotificacionesDTO, NotificacionesDTO> {

    private static final Logger LOGGER = Logger.getLogger(BatchItemProcessor.class.getName());


    @Override
    public NotificacionesDTO process(NotificacionesDTO item) throws Exception {
        if (item.getEmai().isEmpty()){
            LOGGER.info("El email es vacio - ERROR");
            return null;
        }
        if (item.getPhone().isEmpty()){
            LOGGER.info("El phone es vacio, ERROR ");
            return null;
        }
        if (item.getPushId().isEmpty()){
            LOGGER.info("El pushID es vacio, ERROR");
            return null;
        }
        return item;
    }

}
