package com.wizeline.gradle.learningjavagradle.patterns.iterator;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
@WebMvcTest(Client.class)
class ClientTest {

	private static final Logger LOGGER = Logger.getLogger(ClientTest.class.getName());
	Client client = new Client();
	
	@Test
	@DisplayName("prueba cliente iterator")
	void ClientTest() {
		LOGGER.info("Inicia prueba del iterator");
		client.main(null);
	}

}
