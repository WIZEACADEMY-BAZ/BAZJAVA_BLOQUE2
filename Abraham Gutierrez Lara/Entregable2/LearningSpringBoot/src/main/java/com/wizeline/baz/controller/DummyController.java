package com.wizeline.baz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.service.DummyService;

import io.github.bucket4j.Bucket;

@RestController
@RequestMapping("/dummy")
public class DummyController {
	
	@Autowired
	private DummyService dummyService;
	@Autowired
	private Bucket bucket;
	
	@GetMapping("/postalcode/{code}")
	public ResponseEntity<BaseResponseDTO> getPostalCodeInfo(@PathVariable String code) {
		if(!bucket.tryConsume(1))
	        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		return dummyService.getPostalCodeInfo(code);
	}
	
}
