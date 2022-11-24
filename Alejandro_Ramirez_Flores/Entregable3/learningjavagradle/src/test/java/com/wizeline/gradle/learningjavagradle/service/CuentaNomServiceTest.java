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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


class CuentaNomServiceTest {
	
	private static final Logger LOGGER = Logger.getLogger(CuentaNomServiceTest.class.getName());
	
	private MockMvc mockMvc;
	
	@Spy
	@InjectMocks
	private CuentaNomService cuentaNomService;
	
	@Autowired
	BankingAccountNominaRepository dao;
	
	@Autowired
	MongoTemplate mt;

	@Test
	void testObtenerCuenta() {
		//BasicQuery.addCriteria(new Criteria(""));
		String user = "user1";
		LOGGER.info("Obtener nueva cuenta");
		Optional<BankAccountNomina> resp = dao.obtenerCuenta(user);
		assertNotNull(resp);
		Query query = new Query();
	     query.addCriteria(Criteria.where("user").is(user));
		mt.findOne(query, BankAccountNomina.class);
		LOGGER.info("en caso de que los datos sean correctos");
	}
	
	@Test
	void testObtenerCuentaIn() {
		LOGGER.info("Obtener nueva cuenta");
		Optional<BankAccountNomina> resp = dao.obtenerCuenta(null);
		assertNotNull(resp);
		Query query = new Query();
	     query.addCriteria(Criteria.where("user").is(null));
		mt.findOne(query, BankAccountNomina.class);
		LOGGER.info("en caso de que los datos sean Incorrectos");
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
		mt.save(request,BankAccountNomina.class.getName());
		LOGGER.info("en caso de que los datos sean correctos");
	}

	@Test
	void testCreateNominaIn() {
		LOGGER.info("Prueba del metodo CreateNomina");
		BankAccountNomina request = new BankAccountNomina();
		request.setNombreUser(null);
		request.setApellidosUser(null);
		request.setRfc(null);
		ResponseEntity<?> respo = cuentaNomService.createNomina(request);	
		assertNotNull(respo);
		mt.save(request,BankAccountNomina.class.getName());
		LOGGER.info("en caso de que los datos sean Incorrectos");
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
		Query query = new Query();
	    query.addCriteria(Criteria.where("accountNumber").is(request.getAccountNumber()));
		mt.findAndReplace(query,request);
		LOGGER.info("en caso de que los datos sean correctos");
	}

	@Test
	void testUpdateNominaIn() {
		LOGGER.info("Prueba del metodo updateNomina");
		BankAccountNomina request = new BankAccountNomina();
		request.setNombreUser(null);
		request.setApellidosUser(null);
		request.setRfc(null);
		ResponseEntity<?> resp1 = cuentaNomService.updateNomina(request);
		assertNotNull(resp1);
		Query query = new Query();
	    query.addCriteria(Criteria.where("accountNumber").is(request.getAccountNumber()));
		mt.findAndReplace(query,request);
		LOGGER.info("en caso de que los datos sean Incorrectos o nulos");
	}
	
	@Test
	void testDeleteNomina() {
		LOGGER.info("Realizando Prueba del metodo DeleteNomina");
		long accountNumber = 10;
		ResponseEntity<?> respo1 = cuentaNomService.deleteNomina(accountNumber);
		assertNotNull(respo1);
		Query query = new Query();
	    query.addCriteria(Criteria.where("accountNumber").is(accountNumber));
	    mt.remove(query, BankAccountNomina.class);
		LOGGER.info("Para el caso de que los datos sean correctos");
	}
	
	@Test
	void testDeleteNominaIn() {
		LOGGER.info("Realizando Prueba del metodo DeleteNomina");
		long accountNumber = (Long) null;
		ResponseEntity<?> respo1 = cuentaNomService.deleteNomina(accountNumber);
		assertNotNull(respo1);
		Query query = new Query();
	    query.addCriteria(Criteria.where("accountNumber").is(accountNumber));
	    mt.remove(query, BankAccountNomina.class);
		LOGGER.info("en caso de que los datos sean Incorrectos o nulos");
	}

}
