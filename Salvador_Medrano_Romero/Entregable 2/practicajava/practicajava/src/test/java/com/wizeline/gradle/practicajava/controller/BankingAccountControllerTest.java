package com.wizeline.gradle.practicajava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.wizeline.gradle.practicajava.model.BankAccountDTO;
import com.wizeline.gradle.practicajava.service.BankAccountService;

@ContextConfiguration(classes = { BankingAccountController.class })
@ExtendWith(SpringExtension.class)
class BankingAccountControllerTest {

	@MockBean
	private BankAccountService bankAccountService;

	@Autowired
	private BankingAccountController bankingAccountController;

	@MockBean
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@Test
	void sendUserAccountTest() throws Exception {
		List<BankAccountDTO> cuentas = new ArrayList<>();
		BankAccountDTO cuenta = new BankAccountDTO();
		cuenta.setAccountName("cuenta1");
		cuentas.add(cuenta);
		when(bankAccountService.getAccounts()).thenReturn(cuentas);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/apiBank/send/0");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void deleteAccountsTest() throws Exception {
		doNothing().when(bankAccountService).deleteAccounts();
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/apiBank/deleteAccounts");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("All accounts deleted"));
	}

	@Test
	void deleteAccountsTest2() throws Exception {
		doNothing().when(bankAccountService).deleteAccounts();
		MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/apiBank/deleteAccounts");
		deleteResult.characterEncoding("Encoding");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(deleteResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("All accounts deleted"));
	}

	@Test
	void getAccountsByUserTest() throws Exception {
		when(bankAccountService.getAccountsByUser((String) any())).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiBank/getAccountByUser")
				.param("user", "values");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void getAccountsTest() throws Exception {
		when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiBank/getAccounts");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void getAccountsTest2() throws Exception {
		when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/apiBank/getAccounts");
		getResult.characterEncoding("Encoding");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(getResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}
	
	@Test
	void sayHelloGuestTest() throws Exception {
		MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/apiBank/sayHello");
		MockMvcBuilders.standaloneSetup(bankingAccountController).build().perform(getResult)
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
