package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wizeline.gradle.learningjavagradle.enums.AccountType.NOMINA;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class AccountFabricaTest {
	
	@InjectMocks
    AccountFabrica accountFabrica;

	@Test
	void testGetCuenta() {
		accountFabrica.getCuenta(NOMINA);
	}

}
