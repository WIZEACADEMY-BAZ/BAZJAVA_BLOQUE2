package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        LOGGER.info(String.format("Message sent -> %s", message));
        this.kafkaTemplate.send(Constants.TOPIC_NAME, message);
    }
}
