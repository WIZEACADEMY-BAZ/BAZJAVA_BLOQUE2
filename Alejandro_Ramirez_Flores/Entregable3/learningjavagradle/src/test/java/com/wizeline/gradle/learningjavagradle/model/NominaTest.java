package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class NominaTest {
	
	    @InjectMocks
	    private Nomina nomina;

	@Test
	void testGetAccountNumber() {
		nomina.getAccountNumber();
	}

	@Test
	void testSetAccountNumber() {
		nomina.setAccountNumber(123);
	}

	@Test
	void testGetEfectivo() {
		nomina.getEfectivo();
	}

	@Test
	void testSetEfectivo() {
		 nomina.setEfectivo(123);
	}

	@Test
	void testTieneFondos() {
		nomina.tieneFondos();
	}

	@Test
	void testCuentaValida() {
	}

}
