package com.wizeline.baz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpringBootApplication;
import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.model.Place;
import com.wizeline.baz.model.PostalCodeInfo;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.PostalCodeResponse;
import com.wizeline.baz.repository.DummyDao;
import com.wizeline.baz.utils.StatusCodes;

import io.github.bucket4j.Bucket;

@AutoConfigureMockMvc
@AutoConfigureDataMongo
@ActiveProfiles({"test"})
@SpringBootTest(classes = LearningSpringBootApplication.class)
class DummyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DummyDao dummyDao;
	@MockBean
	private Bucket bucket;
	
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final String BASE_URL = "/dummy/postalcode/{postalCode}";
	
	@Test
	void realPostalCode() throws Exception {
		when(bucket.tryConsume(1)).thenReturn(true);
		when(dummyDao.getPostalCodeInfo(anyString(), anyString()))
						.thenReturn(Optional.of(getPostalCodeInfo()));
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL, "04660"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		
		MockHttpServletResponse servletResponse = result.getResponse();
		assertNotNull(servletResponse);
		assertEquals(HttpStatus.OK.value(), servletResponse.getStatus());
		String responseBodyStr = servletResponse.getContentAsString();
		assertNotNull(responseBodyStr);
		PostalCodeResponse responseBody = JSON_MAPPER.readValue(responseBodyStr, PostalCodeResponse.class);
		assertNotNull(responseBody);
		assertEquals(StatusCodes.SUCESS, responseBody.getCode());
		assertEquals(ResponseStatus.OK, responseBody.getStatus());
		assertNull(responseBody.getErrors());
		PostalCodeInfo postalCodeInfo = responseBody.getPostalCodeInfo();
		assertNotNull(postalCodeInfo);
		assertNotNull(postalCodeInfo.getCountry());
		assertNotNull(postalCodeInfo.getCountryAbbreviation());
		assertNotNull(postalCodeInfo.getPostalCode());
		assertNotNull(postalCodeInfo.getPlaces());
	}
	
	@Test
	void fakePostalCode() throws Exception {
		when(bucket.tryConsume(1)).thenReturn(true);
		when(dummyDao.getPostalCodeInfo(anyString(), anyString()))
						.thenReturn(Optional.empty());
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL, "04660"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		
		MockHttpServletResponse servletResponse = result.getResponse();
		assertNotNull(servletResponse);
		assertEquals(HttpStatus.NOT_FOUND.value(), servletResponse.getStatus());
		String responseBodyStr = servletResponse.getContentAsString();
		assertNotNull(responseBodyStr);
		BaseResponseDTO responseBody = JSON_MAPPER.readValue(responseBodyStr, BaseResponseDTO.class);
		assertNotNull(responseBody);
		assertEquals(StatusCodes.FAILED, responseBody.getCode());
		assertEquals(ResponseStatus.FAILED, responseBody.getStatus());
		assertNotNull(responseBody.getErrors());
	}
	
	@Test
	void tooManyRequest() throws Exception {
		when(bucket.tryConsume(1)).thenReturn(false);
		when(dummyDao.getPostalCodeInfo(anyString(), anyString()))
						.thenReturn(Optional.empty());
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL, "04660"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		
		MockHttpServletResponse servletResponse = result.getResponse();
		assertNotNull(servletResponse);
		assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(), servletResponse.getStatus());
	}
	
	private PostalCodeInfo getPostalCodeInfo() {
		PostalCodeInfo postalCodeInfo = new PostalCodeInfo();
		postalCodeInfo.setCountry("Mexico");
		postalCodeInfo.setCountryAbbreviation("mx");
		postalCodeInfo.setPlaces(new Place[] {});
		postalCodeInfo.setPostalCode("04660");
		return postalCodeInfo;
	}
	
}
