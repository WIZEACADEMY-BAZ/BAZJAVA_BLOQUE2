package com.wizeline.gradle.learningjavagradle.patterns.singleton;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(SingletonImpl.class)
class SingletonImplTest {

	private static final Logger LOGGER = Logger.getLogger(SingletonImpl.class.getName());
	SingletonImpl singleton;
	
	@Test
	@DisplayName("Prueba de singleton correcto")
	void singleton() {
		LOGGER.info("inicia prueba de singleton");
		singleton.main(null);
	}
	
	@Test
	@DisplayName("prueba de singleton al crear otra instancia")
	void singletonIn() {
		LOGGER.info("inicia prueba de singleton");
		LOGGER.info("genera primer instancia");
		singleton.main(null);
		LOGGER.info("ya no genera la instancia por que ya existe una");
		singleton.main(null);
	}

}
