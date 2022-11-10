package com.wizeline.baz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.service.DummyService;

@RestController
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
	private DummyService dummyService;
	
	@GetMapping("/postalcode/{code}")
	public ResponseEntity<BaseResponseDTO> getPostalCodeInfo(@PathVariable String code) {
		return dummyService.getPostalCodeInfo(code);
	}
	
}
