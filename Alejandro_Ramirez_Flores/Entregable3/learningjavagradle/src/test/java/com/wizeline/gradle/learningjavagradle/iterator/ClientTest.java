package com.wizeline.gradle.learningjavagradle.iterator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(Client.class)
class ClientTest {
	
	private static final Logger LOGGER = Logger.getLogger(ClientTest.class.getName());

	@Test
	void testMain() {
		Client client = new Client();
		LOGGER.info("Prueba del iterator");
		client.main(null);
	}

}
