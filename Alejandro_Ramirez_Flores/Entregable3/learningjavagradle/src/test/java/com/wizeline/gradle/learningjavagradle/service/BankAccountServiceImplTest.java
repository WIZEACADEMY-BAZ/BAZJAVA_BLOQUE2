package com.wizeline.gradle.learningjavagradle.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountRepository;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.mongodb.assertions.Assertions;
import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.enums.Country;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountServiceImplTest {
	private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImplTest.class.getName());
	
	@Mock
    BankingAccountRepository bankAccountRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

	
	@Test
	void testGetAccounts() {
		LOGGER.info("Prueba de Servicio getAccounts()");
		List<BankAccountDTO> bankAccountDTO = bankAccountService.getAccounts();
        assertNotNull(bankAccountDTO);
        LOGGER.info("Condicion a cumplir");
						when(bankAccountRepository.findAll()).thenReturn(bankAccountDTO);
						List<BankAccountDTO> accountDTOList = new ArrayList<>();
						assertNotNull(accountDTOList);
						assertEquals(accountDTOList, bankAccountDTO);
	}

	@Test
	void testGetAccountDetails() {
		LOGGER.info("Prueba de Servicio getAccountDetails()");
		BankAccountDTO bankAccount = bankAccountService.getAccountDetails("alex","21-11-2022");
        assertNotNull(bankAccount);
        LOGGER.info("Condiciones a cumplir en el servicio");
		BankAccountDTO response = new BankAccountDTO();
		assertEquals(bankAccount.getAccountNumber(), response.getAccountNumber());
		assertEquals(bankAccount.getAccountName(), response.getAccountName());
		assertEquals(bankAccount.getAccountType(), response.getAccountType());
	}

	@Test
	void testDeleteAccounts() {
		LOGGER.info("Prueba de Servicio deleteAccounts()");
		 bankAccountService.deleteAccounts();
	     verify(bankAccountRepository,times(1)).deleteAll();
	}

	@Test
	void testGetAccountByUser() {
		LOGGER.info("Prueba de Servicio getAccountByUser()");
        BankAccountDTO account = new BankAccountDTO();
		account.setAccountName("cuenta_Alex N.1");
		BankAccountDTO account2 = new BankAccountDTO();
		account2.setAccountName("Cuenta_Alex N.2");
		List<Object> lista = new ArrayList<Object>();
		lista.add(account);
		lista.add(account2);
		Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.any())).thenReturn(lista);
        List<BankAccountDTO> resp= bankAccountService.getAccountByUser("alex");
        assertNotNull(resp);
		LOGGER.info("Lista de Cuentas por Usuario "+resp.toString());
        Assertions.assertNotNull(resp);
	}

}
