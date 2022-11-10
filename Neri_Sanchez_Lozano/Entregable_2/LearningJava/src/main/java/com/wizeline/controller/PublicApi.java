package com.wizeline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wizeline.model.ActividadDTO;
import com.wizeline.service.ActivityService;

@RequestMapping("/api")
@RestController
public class PublicApi {

	
	@Autowired
	private ActivityService activityService;
	
	@GetMapping("/restTemplateConsume")
	public ResponseEntity<ActividadDTO> restTemplateConsume(){
		ResponseEntity<ActividadDTO> response = activityService.getActivity();
		return new ResponseEntity<ActividadDTO>(response.getBody(),HttpStatus.OK);
	}	

}
