package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class AhorroTest {
	
	    @InjectMocks
	    private Ahorro ahorro;

	@Test
	void testGetAccountNumber() {
		ahorro.getAccountNumber();
	}

	@Test
	void testSetAccountNumber() {
		ahorro.setAccountNumber(1);
	}

	@Test
	void testGetEfectivo() {
		 ahorro.getEfectivo();
	}

	@Test
	void testSetEfectivo() {
		ahorro.setEfectivo(22);
	}

	@Test
	void testTieneFondos() {
		ahorro.tieneFondos();
	}

	@Test
	void testCuentaValida() {
		ahorro.cuentaValida();
	}

}
