package com.cursojava.proyecto.producer;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class Producer {
    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());
    @KafkaListener(id = "producerGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void produceMessage() {
        String topic = "useraccount-topic";
        /*KafkaProducer<String, String> productor = new KafkaProducer<>();
        try {
            productor.send(new ProducerRecord<>(topic, "Un mensaje desde Java"));
            productor.send(new ProducerRecord<>(topic, "Otro mensaje desde Java"));
            productor.send(new ProducerRecord<>(topic, "Un tercer mensaje desde Java"));
        } finally {
            productor.flush();
            productor.close();
        }*/
    }
}