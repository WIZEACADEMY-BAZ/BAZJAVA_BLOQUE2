package com.cursojava.proyecto.consumer;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumer.class.getName());
    @KafkaListener(id = "consumerGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<EntrenadorDTO>> cr, @Payload EntrenadorDTO entrenadorDTO) {
        LOGGER.info("Received: " + entrenadorDTO.getNombre());
        System.out.println("Received: " + entrenadorDTO.getNombre());
    }
}