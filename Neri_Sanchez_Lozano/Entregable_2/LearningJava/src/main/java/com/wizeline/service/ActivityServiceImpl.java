package com.wizeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wizeline.model.ActividadDTO;

@Service
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.activity}")
	private String apiActivity;

	@Override
	public ResponseEntity<ActividadDTO> getActivity() {
		return restTemplate.getForEntity(apiActivity, ActividadDTO.class);
	}

}
