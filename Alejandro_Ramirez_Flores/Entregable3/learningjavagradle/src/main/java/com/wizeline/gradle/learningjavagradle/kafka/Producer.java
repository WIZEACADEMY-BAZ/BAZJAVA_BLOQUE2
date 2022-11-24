package com.wizeline.gradle.learningjavagradle.kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;

public class Producer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void producerPrueba(String dato){
        kafkaTemplate.send("useraccount-topic", dato);
    }
}
