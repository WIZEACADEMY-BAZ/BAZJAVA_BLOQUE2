package com.wizeline.gradle.learningjavagradle.kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Consumer {
	  @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
	    public void consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
	    }
}
