package com.wizeline.gradle.learningjavagradle.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

public class UserWriter implements ItemWriter<String>{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWriter.class);
	
	@Override
	public void write(List<? extends String> list) throws Exception{
		for(String data: list) {
			LOGGER.info("UserWriter: writing data: " + data);
		}
		LOGGER.info("UserWriter: Writing data completed!!");
	}

}
