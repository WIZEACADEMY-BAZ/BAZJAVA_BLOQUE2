package com.wizeline.maven.learningjavamaven.consumer;

import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountModel>> cr, @Payload BankAccountModel account) {
        System.out.println("Received: " + account.getUserName());
    }
}
