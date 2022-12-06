package baz.practice.wizeline.learningjavamaven.consumer;

import java.util.List;

import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


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


