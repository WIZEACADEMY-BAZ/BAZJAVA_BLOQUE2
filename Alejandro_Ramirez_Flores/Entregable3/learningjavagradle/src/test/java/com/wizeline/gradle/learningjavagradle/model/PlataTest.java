package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class PlataTest {
	
	@InjectMocks
    private Plata plata;

	@Test
	void testGetAccountNumber() {
		plata.getAccountNumber();
	}

	@Test
	void testSetAccountNumber() {
		plata.setAccountNumber(12);
	}

	@Test
	void testGetEfectivo() {
		plata.getEfectivo();
	}

	@Test
	void testSetEfectivo() {
		plata.setEfectivo(20);
	}

	@Test
	void testTieneFondos() {
		plata.tieneFondos();
	}

	@Test
	void testCuentaValida() {
		plata.cuentaValida();
	}

}
