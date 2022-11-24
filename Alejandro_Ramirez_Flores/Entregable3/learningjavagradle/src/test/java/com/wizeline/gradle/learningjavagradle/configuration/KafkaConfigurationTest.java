package com.wizeline.gradle.learningjavagradle.configuration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ProducerFactory;

import com.mongodb.assertions.Assertions;

@WebMvcTest(KafkaConfigurationTest.class)
class KafkaConfigurationTest {
	
	private static final Logger LOGGER = Logger.getLogger(KafkaConfigurationTest.class.getName());
	
	@MockBean
	private KafkaProperties kafkaProperties;

	@Autowired
	KafkaConfiguration kafka;

	@Test
	void testConfigKafka() {
		LOGGER.info("prueba de configuracion de kafka");
		ProducerFactory<Object, Object> producer=kafka.producerFactory();
		Assertions.assertNotNull(producer);
	}

}
