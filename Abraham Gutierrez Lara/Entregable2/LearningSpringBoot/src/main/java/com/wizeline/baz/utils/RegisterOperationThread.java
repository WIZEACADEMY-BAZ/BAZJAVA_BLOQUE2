package com.wizeline.baz.utils;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.model.OperationData;

public class RegisterOperationThread extends Thread {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterOperationThread.class.getName());
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	private final OperationData operationData;
	private final String topic;
	
	public RegisterOperationThread(OperationData operationData, String topic) {
		this.operationData = operationData;
		this.topic = topic;
	}
	
	@Override
	public void run() {
		LOGGER.info("Sendig operation data to topic " + topic);
		try {
			LOGGER.info(jsonMapper.writeValueAsString(operationData));
		} catch (JsonProcessingException e) {
			LOGGER.info("Failed sending operation data");
		}
	}
}
