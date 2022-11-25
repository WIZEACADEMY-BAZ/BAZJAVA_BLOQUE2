package com.wizeline.maven.learningjavamaven.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void producerPrueba(String dato){
        kafkaTemplate.send("useraccount-topic", dato);
    }
}
