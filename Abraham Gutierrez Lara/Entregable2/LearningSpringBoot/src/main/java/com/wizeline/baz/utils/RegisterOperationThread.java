package com.wizeline.baz.utils;

import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.wizeline.baz.model.KafkaMessage;
import com.wizeline.baz.model.OperationData;

public class RegisterOperationThread extends Thread {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterOperationThread.class.getName());
	
	@Autowired
	private KafkaTemplate<String, KafkaMessage> kafkaTemplate;
	
	private final OperationData operationData;
	private final String topic;
	
	public RegisterOperationThread(OperationData operationData, String topic) {
		this.operationData = operationData;
		this.topic = topic;
	}
	
	@Override
	public void run() {
		final String kafkaMessageId = UUID.randomUUID().toString();
		KafkaMessage message = new KafkaMessage(kafkaMessageId, operationData.getFlowName(), operationData.getData());
		kafkaTemplate.send(topic, kafkaMessageId, message);
		LOGGER.info(String.format("Kafka message send; topic: '%s', flow: '%s', messageId: '%s' ",
											topic, operationData.getFlowName(), kafkaMessageId ));
	}
}
