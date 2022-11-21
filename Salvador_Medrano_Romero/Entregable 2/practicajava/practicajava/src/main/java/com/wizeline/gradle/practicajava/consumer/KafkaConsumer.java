package com.wizeline.gradle.practicajava.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "estudiante-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<EstudianteDTO>> cr, @Payload EstudianteDTO estudiante) {
        System.out.println("Received: " + estudiante.getMatricula());
    }

}