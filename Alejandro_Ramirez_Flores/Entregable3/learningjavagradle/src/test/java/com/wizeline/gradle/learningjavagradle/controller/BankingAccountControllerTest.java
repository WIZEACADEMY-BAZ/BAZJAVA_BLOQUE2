package com.wizeline.gradle.learningjavagradle.controller;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.gradle.learningjavagrade.client.AccountsJSONClient;
import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.Account;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.Post;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wizeline.gradle.learningjavagradle.utils.Utils;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.ExcepcionGenerica;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankingAccountControllerTest {
	 private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());
	
	 @InjectMocks
	    private BankingAccountController bankingController;

	    @Mock
	    private BankAccountService bankAccountService;

	    @Mock
	    private AccountsJSONClient accountsJSONClient;

	    @Mock
	    private KafkaTemplate<Object, Object> template;
	    
	    @InjectMocks
		Utils utils;

	@Test
	void testGetUserAccount() {
		LOGGER.info("Realizando Prueba, parametros del metodo (getUserAccount(user, password, date))");
		  String user = "user1@wizeline.com";
	        String password ="Pass1$";
	        String date = "12-03-1912";
	        ResponseEntity<?> httpResponse = bankingController.getUserAccount(user, password, date);
	        assertNotNull(httpResponse);
	        LOGGER.info("Hacemos no nulo a httpResponse y comparamos la igualdad");
	        assertEquals(httpResponse, HttpStatus.OK);
	}

	@Test
	void testGetAccounts() {
		LOGGER.info("Realizando Prueba");
         bankingController.getAccounts();
         LOGGER.info("Verificando bankAccountService");
        verify(bankAccountService,times(1)).getAccounts();
	}

	@Test
	void testGetAccountsGroupByType() throws JsonProcessingException {
		LOGGER.info("Preparando Prueba");
		List<BankAccountDTO> accounts = bankAccountService.getAccounts();
		LOGGER.info("Condicion para realizar Prueba");
        when(bankAccountService.getAccounts()).thenReturn(accounts);
        ResponseEntity<?> httpResponse = bankingController.getAccountsGroupByType();
        LOGGER.info("Tiene que cumplirse la igualdad del metodo: getAccountsGroupByType()");
        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testDeleteAccounts() {
		   LOGGER.info("Preparando la prueba");
		   ResponseEntity responseEntity = bankingController.deleteAccounts();
		   assertNotNull(responseEntity);
		   LOGGER.info("Verificando la Salida del metodo: DeleteAccounts()");
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	}

	@Test
	void testGetAccountByUser() {
		 LOGGER.info("Preparando Esenario para la Prueba");
		 String user = "user";
	        BankAccountDTO bankAccountDTO= new BankAccountDTO();
	        List<BankAccountDTO> accounts = new ArrayList<>();
	        accounts.add(bankAccountDTO);
	        LOGGER.info("Verificando Salida del metodo: getAccountByUser()");
	        when(bankAccountService.getAccountByUser(user)).thenReturn(accounts);
	        ResponseEntity<?> httpResponse = bankingController.getAccountByUser(user);
	        LOGGER.info("Verificando igualdad");
	        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void testSayHelloGuest() {
		LOGGER.info("Prueba del metodo sayHelloGuest()");
		ResponseEntity<String> httpResponse = bankingController.sayHelloGuest();
		LOGGER.info("Verificando igualdad");
        assertEquals("Hola invitado!!", httpResponse.getBody());
	}

	@Test
	void testGetExternalUser() {
		   LOGGER.info("Preparando Prueba");
		   Post post = new Post("userId", 1L, "title", "body");
	        when(accountsJSONClient.getPostById(1L)).thenReturn(post);
	        ResponseEntity<Post> responseEntity = bankingController.getExternalUser(1L);
	        LOGGER.info("Igualdades a cumplir del metodo GetExternalUser()");
	        assertAll(
	                () -> assertNotNull(responseEntity),
	                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
	                () -> assertEquals(post, responseEntity.getBody())
	        );
	}

	@Test
	void testSendUserAccount() {
		    LOGGER.info("Preparando Prueba del metodo sendUserAccount()");
		    BankAccountDTO bankAccountDTO = new BankAccountDTO();
	        List<BankAccountDTO> accounts = new ArrayList<>();
	        accounts.add(bankAccountDTO);
	        when(bankAccountService.getAccounts()).thenReturn(accounts);
	        bankingController.sendUserAccount(0);
	        LOGGER.info("Igualdad a cumplir");
	        assertEquals(1, accounts.size());
	}

	@Test
	void testFabrica() {
		LOGGER.info("Realizando la Prueba.");
        ResponseEntity<List<Account>> r =   bankingController.Fabrica();
        assertTrue(r.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
	}

}
