package com.wizeline.service;

import org.springframework.http.ResponseEntity;

import com.wizeline.model.ActividadDTO;

public interface ActivityService {
	
	ResponseEntity<ActividadDTO> getActivity();

}
