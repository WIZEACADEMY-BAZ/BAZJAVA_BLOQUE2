package com.wizeline.gradle.learningjavagradle.config;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.ProducerFactory;

@WebMvcTest(KafkaConfiguration.class)
class KafkaConfigTest {
	private static final Logger LOGGER = Logger.getLogger(KafkaConfiguration.class.getName());
	
	@MockBean
	private KafkaProperties kafkaProperties;
	
	@Autowired
	KafkaConfiguration kc;
	
	@Test
	void test() {
		LOGGER.info("prueba de configuracion de kafka");
		ProducerFactory<Object, Object> producer=kc.producerFactory();
		Assertions.assertNotNull(producer);
	}

}
