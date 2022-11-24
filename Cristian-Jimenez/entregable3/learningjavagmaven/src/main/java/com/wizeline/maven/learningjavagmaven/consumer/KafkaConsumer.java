package com.wizeline.maven.learningjavagmaven.consumer;
import java.util.List;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sampleGroup", topics = "useraccount-topic", containerFactory = "jsonKafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, List<BankAccountModel>> cr, @Payload BankAccountModel account) {
        System.out.println("Received: " + account.getUser());
    }
}