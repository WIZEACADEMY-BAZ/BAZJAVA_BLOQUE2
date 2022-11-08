package com.wizeline.baz.utils;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.model.OperationData;

public class RegisterOperationThread<T extends BuildOperationData> extends Thread {
	
	private static final Logger LOGGER = Logger.getLogger(RegisterOperationThread.class.getName());
	
	private ObjectMapper jsonMapper = new ObjectMapper();
	private OperationData operationData;
	
	public RegisterOperationThread(T buildOperationData) {
		this.operationData = buildOperationData.operationData();
	}
	
	@Override
	public void run() {
		LOGGER.info("Sendig operation data...");
		try {
			LOGGER.info(jsonMapper.writeValueAsString(operationData));
		} catch (JsonProcessingException e) {
			LOGGER.info("Failed sending operation data");
		}
	}
}
