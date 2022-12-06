package com.wizeline.learningjavamaven.consumer;

import com.wizeline.learningjavamaven.model.BankAccountDTO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

  @InjectMocks
  KafkaConsumer kafkaConsumer;

  @Test
  void consumeMessage() {
    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    bankAccountDTO.setUserName("username");
    ConsumerRecord cr = new ConsumerRecord<>("topic", 1, 12, "key", "value");
    kafkaConsumer.consumeMessage(cr, bankAccountDTO);
  }
}