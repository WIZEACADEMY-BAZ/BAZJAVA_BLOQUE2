package com.baz.wizeline.learningspring.consumer;

import com.baz.wizeline.learningspring.model.BankAccountDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    @InjectMocks
    KafkaConsumer kafkaConsumer;

    @Test
    void consumeMessage() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setUserName("volcomDoom");
        ConsumerRecord consumer = new ConsumerRecord<>("useraccount-topic", 2, 6, "llave", "valor");
        kafkaConsumer.consumeMessage(consumer, bankAccountDTO);
    }
}