package com.wizeline.gradle.learningjavagradle.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.concurrent.ListenableFuture;

import com.wizeline.gradle.learningjavagradle.client.AccountsJSONClient;
import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.Post;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;


@WebMvcTest(BankingAccountController.class)
class BankingAccountTest {
	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	
	@MockBean
	private BankAccountService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountsJSONClient accountsJSONClient;
	
	@MockBean
	private KafkaTemplate<Object, Object> template;
	
	@Test
	@DisplayName("getUserAccount")
	void getUserAccount() throws Exception {
		LOGGER.info("obtener cuenta de usuario desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		Mockito.when(service.getAccountDetails(Mockito.anyString(), Mockito.anyString())).thenReturn(account);
		mockMvc.perform(
			      get("/api/account/getUserAccount")
			      .param("user", "David")
			      .param("date", "250122")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("getAccounts")
	void getAccounts() throws Exception {
		LOGGER.info("obtener todas las cuentas desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		List<BankAccountDTO> lista = new ArrayList<BankAccountDTO>();
		lista.add(account);
		Mockito.when(service.getAccounts()).thenReturn(lista);
		mockMvc.perform(
			      get("/api/account/getAccounts")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("getAccountsGroupByType")
	void getAccountsGroupByType() throws Exception {
		LOGGER.info("Agrupar las cuentas por tipo desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountType(AccountType.NOMINA);
		BankAccountDTO account2 = new BankAccountDTO();
		account2.setAccountType(AccountType.NOMINA);
		List<BankAccountDTO> lista = new ArrayList<BankAccountDTO>();
		lista.add(account);
		lista.add(account2);
		Mockito.when(service.getAccounts()).thenReturn(lista);
		mockMvc.perform(
			      get("/api/account/getAccountsGroupByType")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("getAccountByUser")
	void getAccountByUser() throws Exception {
		LOGGER.info("obtener cuenta por usuario desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		BankAccountDTO account2 = new BankAccountDTO();
		List<BankAccountDTO> lista = new ArrayList<BankAccountDTO>();
		lista.add(account);
		lista.add(account2);
		Mockito.when(service.getAccountByUser(Mockito.anyString())).thenReturn(lista);
		mockMvc.perform(
			      get("/api/account/getAccountByUser")
			      .param("user", "David")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("deleteAccounts")
	void deleteAccounts() throws Exception {
		LOGGER.info("eliminar todas las cuentas desde el controller");
		doNothing().when(service).deleteAccounts();
		mockMvc.perform(
			      delete("/api/account/deleteAccounts")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("sayHello")
	void sayHello() throws Exception {
		LOGGER.info("prueba invitado desde el controller");
		mockMvc.perform(
			      get("/api/account/sayHello")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("deposito")
	void deposito() throws Exception {
		LOGGER.info("Deposito a cuenta desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountBalance(288.0);
		Mockito.when(service.getAccountByName(Mockito.anyString())).thenReturn(account);
		mockMvc.perform(
			      get("/api/account/deposito")
			      .param("nombre", "cuenta de david")
			      .param("cantidad", "25.6")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("deposito Fallido")
	void depositoFallido() throws Exception {
		LOGGER.info("deposito fallido desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountBalance(288.0);
		Mockito.when(service.getAccountByName(Mockito.anyString())).thenReturn(null);
		mockMvc.perform(
			      get("/api/account/deposito")
			      .param("nombre", "cuenta de david")
			      .param("cantidad", "25.6")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("getExternalUser")
	void getExternalUser() throws Exception {
		LOGGER.info("@FeignClient desde el controller");
		Post post = new Post();
		Mockito.when(accountsJSONClient.getPostById(Mockito.anyLong())).thenReturn(post);
		mockMvc.perform(
			      get("/api/account/getExternalUser/1")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	
	@Test
	@DisplayName("send")
	void send() throws Exception {
		LOGGER.info("Envio de usuario a kafka desde el controller");
		BankAccountDTO account = new BankAccountDTO();
		BankAccountDTO account2 = new BankAccountDTO();
		List<BankAccountDTO> lista = new ArrayList<BankAccountDTO>();
		lista.add(account);
		lista.add(account2);
		ListenableFuture<SendResult<Object,Object>> l = null;
		Mockito.when(service.getAccounts()).thenReturn(lista);
		Mockito.when(template.send(Mockito.anyString(), Mockito.any())).thenReturn(l);
		mockMvc.perform(
			      post("/api/account/send/1")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}
	}