package com.wizeline.baz.service;

import org.springframework.http.ResponseEntity;

import com.wizeline.baz.model.response.BaseResponseDTO;

public interface DummyService {
	
	ResponseEntity<BaseResponseDTO> getPostalCodeInfo(String postal);
	ResponseEntity<BaseResponseDTO> getPostalCodeInfo(String postal, String countryAbbreviation);
}
