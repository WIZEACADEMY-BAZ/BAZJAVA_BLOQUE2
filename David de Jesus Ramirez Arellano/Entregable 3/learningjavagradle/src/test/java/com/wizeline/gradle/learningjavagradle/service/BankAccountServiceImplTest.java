package com.wizeline.gradle.learningjavagradle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountRepository;

@WebMvcTest(BankAccountServiceImpl.class)
class BankAccountServiceImplTest {
	private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());
	
	@MockBean
	BankingAccountRepository repository;
	
	@MockBean 
	MongoTemplate mongoTemplate; 
	
	@Autowired
	BankAccountServiceImpl service;

	@Test
	@DisplayName("getAccounts en service")
	void servicegetAccounts() {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountName("cuenta de david 1");
		BankAccountDTO account2 = new BankAccountDTO();
		account2.setAccountName("Cuenta de David 2");
		List<Object> lista = new ArrayList<Object>();
		lista.add(account);
		lista.add(account2);
		Mockito.when(mongoTemplate.save(Mockito.any())).thenReturn(null);
		Mockito.when(mongoTemplate.findAll(Mockito.any())).thenReturn(lista);
		List<BankAccountDTO> resp= service.getAccounts();
		LOGGER.info("lista de cuentas "+resp.toString());
		Assertions.assertNotNull(resp);
	}

	@Test
	@DisplayName("deleteAccounts en service")
	void deleteAccounts() {
		Mockito.doNothing().when(repository).deleteAll();
		LOGGER.info("Inicia eliminacion de cuentas ");
        service.deleteAccounts();
	}
	
	@Test
	@DisplayName("getAccountDetails en service")
	void getAccountDetails() {
        BankAccountDTO account=service.getAccountDetails("david", "01-09-1989");
		LOGGER.info("Detalle de cuenta: "+account.toString());
        Assertions.assertNotNull(account);
	}
	
	@Test
	@DisplayName("getAccountByUser en service")
	void getAccountByUser() {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountName("cuenta de david 1");
		BankAccountDTO account2 = new BankAccountDTO();
		account2.setAccountName("Cuenta de David 2");
		List<Object> lista = new ArrayList<Object>();
		lista.add(account);
		lista.add(account2);
		Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.any())).thenReturn(lista);
        List<BankAccountDTO> resp=service.getAccountByUser("david");
		LOGGER.info("lista de cuentas por usuario "+resp.toString());
        Assertions.assertNotNull(resp);
	}
	
	@Test
	@DisplayName("getAccountByName en service")
	void getAccountByName() {
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountName("cuenta de david 1");
		Mockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(account);
        BankAccountDTO resp=service.getAccountByName("cuenta de david 1");
		LOGGER.info("cuenta por nombre "+resp.toString());
        Assertions.assertNotNull(resp);
	}
	

}
