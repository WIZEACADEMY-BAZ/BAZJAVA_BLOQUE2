package com.wizeline.baz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpringBootApplication;
import com.wizeline.baz.enums.BankAccountType;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.BankAccountDTO;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.request.CreateAccountRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateBankAccountResponseDTO;
import com.wizeline.baz.repository.BankAccountFactory;
import com.wizeline.baz.repository.BankAccountFactoryImp;
import com.wizeline.baz.service.BankAccountServiceImpl;
import com.wizeline.baz.utils.JwtTokenService;
import com.wizeline.baz.utils.StatusCodes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJws;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ActiveProfiles({"test"})
@SpringBootTest(classes = LearningSpringBootApplication.class)
public class BankAccountControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BankAccountServiceImpl service;
	@MockBean
	private JwtTokenService jwtTokenService;
	private BankAccountFactory bankAccountFactory = BankAccountFactoryImp.getInstance();
	
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final String BASE_URL = "/bank-account";
	
	@Test
	void createAccountWithUserNotFound() throws Exception {
		CreateAccountRequest bankAccountReq = setUpCreateAccountTest("BANKER");
		UserNotFoundException userNotFoundException = new UserNotFoundException(bankAccountReq.getUserId());
		when(service.createAccount(any())).thenThrow(userNotFoundException);
		String jsonRequest = JSON_MAPPER.writeValueAsString(bankAccountReq);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
												.header("Authorization", "Bearer FAKE_JWT")
												.content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
		assertNotNull(result);
		assertNotNull(result.getResponse());
		String responseBodyStr = result.getResponse().getContentAsString();
		assertNotNull(responseBodyStr);
		BaseResponseDTO response = JSON_MAPPER.readValue(responseBodyStr, BaseResponseDTO.class);
		assertNotNull(response);
		ErrorDTO error = response.getErrors();
		assertNotNull(error);
		assertEquals(StatusCodes.USER_DOESNT_EXIST, error.getErrorCode());
		assertNotNull(error.getMessage());
		assertTrue(error.getMessage().contains(bankAccountReq.getUserId()));
	}
	
	@Test
	void createUser() throws Exception {
		CreateAccountRequest bankAccountReq = setUpCreateAccountTest("BANKER");
		CreateBankAccountResponseDTO mockResponse = new CreateBankAccountResponseDTO();
		BankAccountDTO bankAccount = bankAccountFactory.createBankAccount(bankAccountReq.getUserId(),
														bankAccountReq.getAccountType());
		BeanUtils.copyProperties(bankAccount, mockResponse);
		mockResponse.makeSuccess();
		when(service.createAccount(any())).thenReturn(ResponseEntity.ok(mockResponse));
		String jsonRequest = JSON_MAPPER.writeValueAsString(bankAccountReq);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
												.header("Authorization", "Bearer FAKE_JWT")
												.content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
		assertNotNull(result);
		assertNotNull(result.getResponse());
		String responseBodyStr = result.getResponse().getContentAsString();
		assertNotNull(responseBodyStr);
		CreateBankAccountResponseDTO response = JSON_MAPPER.readValue(responseBodyStr, CreateBankAccountResponseDTO.class);
		assertNotNull(response);
		ErrorDTO error = response.getErrors();
		assertNull(error);
		assertNotNull(response.getAccountAlias());
		assertNotNull(response.getAccountType());
	}
	
	private CreateAccountRequest setUpCreateAccountTest(String ...roles) {
		when(jwtTokenService.validateAccessToken(anyString())).thenReturn(getClaims(roles));
		CreateAccountRequest bankAccountReq = new CreateAccountRequest();
		bankAccountReq.setAccountType(BankAccountType.PLATINUM);
		bankAccountReq.setUserId(UUID.randomUUID().toString());
		return bankAccountReq;
	}
	
	private Optional<Jws<Claims>> getClaims(String ...roles) {
		Claims claims = Jwts.claims();
		claims.put("authorities", String.join(",", roles));
		return Optional.of( new DefaultJws<Claims>(null, claims, BASE_URL));
	}
}
