package com.wizeline.gradle.learningjavagradle.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.wizeline.gradle.learningjavagradle.model.XmlBean;

@ExtendWith(MockitoExtension.class)
class JAXBControllerTest {

	private static final Logger LOGGER = Logger.getLogger(JAXBControllerTest.class.getName());

	@InjectMocks
	private JAXBController jAXBController;

	@Test
	void testLoginUser() {
		LOGGER.info("Preparando Prueba del metodo: loginUser()");
		ResponseEntity<XmlBean> response = jAXBController.loginUser();
		assertTrue(response.getStatusCode().is2xxSuccessful(), "Codigo Respuesta EXITOSO");
	}
}
