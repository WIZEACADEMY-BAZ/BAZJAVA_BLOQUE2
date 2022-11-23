package com.wizeline.baz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpringBootApplication;
import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.exceptions.FailedLoginException;
import com.wizeline.baz.model.batch.FailedLoginInfo;
import com.wizeline.baz.model.request.LoginRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.service.UserService;
import com.wizeline.baz.utils.StatusCodes;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ActiveProfiles({"test"})
@SpringBootTest(classes = LearningSpringBootApplication.class)
public class UserControllerTest {
	
	@MockBean
	private UserService service;
	@Autowired
	private MockMvc mockMvc;
	
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final String BASE_URL = "/user";
	
	@Test
	void failedLogin() throws Exception {
		LoginRequest request = new LoginRequest();
		request.setEmail("myemail@emailcom");
		request.setPassword("mypassword");
		FailedLoginInfo failedLoginInfo = new FailedLoginInfo();
		failedLoginInfo.setUserId(UUID.randomUUID().toString());
		failedLoginInfo.setFailedPassword(request.getPassword());
		failedLoginInfo.setEmail(request.getEmail());
		failedLoginInfo.setTime(LocalDateTime.now());
		FailedLoginException failedLoginException = new FailedLoginException(failedLoginInfo.toMap());
		when(service.login(any())).thenThrow(failedLoginException);
		String jsonRequest = JSON_MAPPER.writeValueAsString(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL.concat("/login"))
												.content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
		assertNotNull(result);
		assertNotNull(result.getResponse());
		assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void loginBadRequest() throws Exception {
		LoginRequest request = new LoginRequest();
		request.setPassword("mypassword");
		String jsonRequest = JSON_MAPPER.writeValueAsString(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL.concat("/login"))
												.content(jsonRequest).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andReturn();
		assertNotNull(result);
		MockHttpServletResponse servletResponse = result.getResponse();
		assertNotNull(servletResponse);
		assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		String responseBodyStr = servletResponse.getContentAsString();
		assertNotNull(responseBodyStr);
		BaseResponseDTO responseBody = JSON_MAPPER.readValue(responseBodyStr, BaseResponseDTO.class);
		assertNotNull(responseBody);
		assertEquals(StatusCodes.FAILED, responseBody.getCode());
		assertEquals(ResponseStatus.FAILED, responseBody.getStatus());
		assertNotNull(responseBody.getErrors());
	}
}
