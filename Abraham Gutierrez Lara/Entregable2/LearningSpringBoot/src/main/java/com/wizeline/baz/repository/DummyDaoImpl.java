package com.wizeline.baz.repository;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.model.PostalCodeInfo;

@Component
public class DummyDaoImpl implements DummyDao {
	
	private static final Logger LOGGER = Logger.getLogger(DummyDaoImpl.class.getName());
	
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public Optional<PostalCodeInfo> getPostalCodeInfo(String postalCode, String countryAbbreviation) {
		PostalCodeInfo postalCodeInfo = null;
		try {
			ResponseEntity<String> responseEntity = restTemplate.exchange(String.format("https://api.zippopotam.us/%s/%s", countryAbbreviation, postalCode), HttpMethod.GET, null, String.class);
			if(responseEntity.hasBody()) { 
				LOGGER.info("PostalCode Response: " + responseEntity.getBody());
				postalCodeInfo = mapper.readValue(responseEntity.getBody(), PostalCodeInfo.class);
			}
		} catch (RestClientException ex) {
			LOGGER.info("Something went wrong connecting to PostalCode API");
		}catch (JsonProcessingException ex) {
			LOGGER.info("Something went wrong parsing PostalCode API Response");
		}
		return Optional.ofNullable(postalCodeInfo);
	}

}
