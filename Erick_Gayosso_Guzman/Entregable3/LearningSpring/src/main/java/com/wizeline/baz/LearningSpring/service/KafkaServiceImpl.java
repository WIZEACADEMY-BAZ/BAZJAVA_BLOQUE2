package com.wizeline.baz.LearningSpring.service;

import com.wizeline.baz.LearningSpring.patron.creational.ResponseDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;


@Service
public class KafkaServiceImpl implements KafkaService {

    private static final Logger LOGGER = Logger.getLogger(KafkaServiceImpl.class.getName());

    @Value("${topic.name.producer}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Override
    public ResponseDTO producerKafka(String input) {
        LOGGER.info("Kafka producer - message: " + input);
        kafkaTemplate.send(topicName, input);
        ResponseDTO responseDTO = new ResponseDTO.ResponseDTOBuilder("OK000","SUCCESS").build();
        LOGGER.info(responseDTO.toString());
        return responseDTO;
    }

    @KafkaListener(topics = "${topic.name.producer}", groupId = "wizeline-baz-group")
    @Override
    public void consumerKafka(ConsumerRecord<String, String> payload) throws Exception{
        LOGGER.info("Kafka Consumer - Topic: " + topicName);
        LOGGER.info("Kafka Consumer - Key: " + payload.key());
        LOGGER.info("Kafka Consumer - Headers: " + payload.headers());
        LOGGER.info("Kafka Consumer - Partition: " + payload.partition());
        LOGGER.info("Kafka Consumer - VALUE: " + payload.value());
    }
}
