package com.wizeline.maven.learningjava.Learning.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.wizeline.maven.learningjava.Learning.model.BankAccountDTO;

/**
 * Class Description goes here.
 * Created by enrique.gutierrez on 29/09/22
 */

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountDTO>> cr, @Payload BankAccountDTO account) {
        System.out.println("Received: " + account.getUserName());
    }
}