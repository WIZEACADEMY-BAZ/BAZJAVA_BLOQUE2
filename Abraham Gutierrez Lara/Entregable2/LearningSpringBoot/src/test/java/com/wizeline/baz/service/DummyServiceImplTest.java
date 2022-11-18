package com.wizeline.baz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.baz.model.Place;
import com.wizeline.baz.model.PostalCodeInfo;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.PostalCodeResponse;
import com.wizeline.baz.repository.DummyDao;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class DummyServiceImplTest {
	
	@Mock
	private DummyDao dummyDao;
	@InjectMocks
	private DummyServiceImpl service;
	private PostalCodeInfo postalCodeInfo;
	
	@BeforeAll
	void init() {
		postalCodeInfo = new PostalCodeInfo();
		postalCodeInfo.setCountry("Mexico");
		postalCodeInfo.setCountryAbbreviation("mx");
		postalCodeInfo.setPlaces(new Place[] {});
		postalCodeInfo.setPostalCode("04660");
	}
	
	@Test
	void getNotFoundPostalCode() {
		when(dummyDao.getPostalCodeInfo(anyString(),anyString())).thenReturn(Optional.empty());
		ResponseEntity<BaseResponseDTO> responseEntity = service.getPostalCodeInfo(anyString(), anyString());	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(BaseResponseDTO.class, responseEntity.getBody());
		BaseResponseDTO response = (BaseResponseDTO) responseEntity.getBody();
		assertNotNull(response.getErrors());
	}
	
	@Test
	void getPostalCode() {
		when(dummyDao.getPostalCodeInfo(anyString(),anyString())).thenReturn(Optional.of(postalCodeInfo));
		ResponseEntity<BaseResponseDTO> responseEntity = service.getPostalCodeInfo(anyString(), anyString());	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(PostalCodeResponse.class, responseEntity.getBody());
		PostalCodeResponse response = (PostalCodeResponse) responseEntity.getBody();
		assertNotNull(response.getPostalCodeInfo());
	}
	
	@Test
	void getPostalCodeMx() {
		when(dummyDao.getPostalCodeInfo("", "mx")).thenReturn(Optional.of(postalCodeInfo));		
		ResponseEntity<BaseResponseDTO> responseEntity = service.getPostalCodeInfo("");	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(PostalCodeResponse.class, responseEntity.getBody());
		PostalCodeResponse response = (PostalCodeResponse) responseEntity.getBody();
		assertNotNull(response.getPostalCodeInfo());
	}
	
}
