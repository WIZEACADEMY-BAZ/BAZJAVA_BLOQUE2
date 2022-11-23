package com.wizeline.gradle.learningjavagradle.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.service.CuentaNomService;

@ExtendWith(MockitoExtension.class)
class CuentaNomControllerTest {
	
	 @InjectMocks
	 private CuentaNomController cuentaNomController;
	 
	 @Mock
	 private CuentaNomService cuentaNomService;

	@Test
	void testGetUserAccount() {
	String user = "user";
	
	 ResponseEntity<?> httpResponse = cuentaNomController.getUserAccount(user);
	 assertNotNull(httpResponse);
	 assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testCreateAccountNomina() {
		BankAccountNomina request = new BankAccountNomina();
		request.setNombreUser("alex");
		request.setApellidosUser("ramirez");
		request.setRfc("RAFA881004D85");
		ResponseEntity<?> httpResponse1 = cuentaNomController.createAccountNomina(request );
		 assertNotNull(httpResponse1);
		assertEquals(httpResponse1.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testUpdateAccountNomina() {
		BankAccountNomina requestA = new BankAccountNomina();
		requestA.setNombreUser("alejandro");
		requestA.setApellidosUser("ramirez");
		ResponseEntity<?> httpResponseA = cuentaNomController.createAccountNomina(requestA);
		assertNotNull(httpResponseA);
		assertEquals(httpResponseA.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testDeleteAccountNomina() {
		long AccountNumber=2;
		ResponseEntity<?> httpResponseAccountNumber = cuentaNomController.deleteAccountNomina(AccountNumber);
		assertNotNull(httpResponseAccountNumber);
		assertEquals(httpResponseAccountNumber.getStatusCode(), HttpStatus.OK);
	}

}
