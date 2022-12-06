package com.wizeline.baz.LearningSpring.service;

import com.wizeline.baz.LearningSpring.patron.creational.ResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface KafkaService {

    public ResponseDTO producerKafka(String input);
    public void consumerKafka(ConsumerRecord<String, String> payload) throws Exception;
}
