package com.wizeline.baz.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.wizeline.baz.model.KafkaMessage;
import com.wizeline.baz.utils.Constants;

@Component
public class KafkaConsumers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumers.class);
	
	@KafkaListener(id = Constants.BLOCK_USERS_CONSUMER_GROUP, topics = Constants.BLOCK_USERS_TOPIC, 
			containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, KafkaMessage> record, @Payload KafkaMessage message) {
        LOGGER.info("Se ha bloqueado al usuario " );
    }
}
