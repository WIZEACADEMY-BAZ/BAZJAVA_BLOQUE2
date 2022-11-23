package com.wizeline.gradle.learningjavagradle.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountNominaRepository;

class CuentaNomServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(CuentaNomServiceTest.class.getName());
	
	private MockMvc mockMvc;
	
	@Spy
	@InjectMocks
	private CuentaNomService cuentaNomService;
	
	@Autowired
	BankingAccountNominaRepository dao;

	@Test
	void testObtenerCuenta() {
		String user = "user1";
		LOGGER.info("Obtener nueva cuenta");
		Optional<BankAccountNomina> resp = dao.obtenerCuenta(user);
		assertNotNull(resp);
	}

	@Test
	void testCreateNomina() {
		LOGGER.info("Prueba del metodo CreateNomina");
		BankAccountNomina request = new BankAccountNomina();
		request.setNombreUser("alexander");
		request.setApellidosUser("ramirex");
		request.setRfc("RAFA881004D86");
		ResponseEntity<?> respo = cuentaNomService.createNomina(request);	
		assertNotNull(respo);
	}

	@Test
	void testUpdateNomina() {
		LOGGER.info("Prueba del metodo updateNomina");
		BankAccountNomina request = new BankAccountNomina();
		request.setNombreUser("alexander");
		request.setApellidosUser("ramirex");
		request.setRfc("RAFA881004D86");
		ResponseEntity<?> resp1 = cuentaNomService.updateNomina(request);
		assertNotNull(resp1);
	}

	@Test
	void testDeleteNomina() {
		LOGGER.info("Realizando Prueba del metodo DeleteNomina");
		long accountNumber = 10;
		ResponseEntity<?> respo1 = cuentaNomService.deleteNomina(accountNumber);
		assertNotNull(respo1);
	}

}
