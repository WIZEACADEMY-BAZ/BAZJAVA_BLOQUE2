package com.wizeline.gradle.learningjavagradle.repository;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.wizeline.gradle.learningjavagradle.model.NuevoDTO;

@Repository
public class NuevoRepositoryImpl implements NuevoRepository{

	@SuppressWarnings("unchecked")
	@Override
	public List<NuevoDTO> getNuevoUsuario(String usuarioId) {
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NuevoDTO> responseEntity =  restTemplate.getForEntity("http://localhost:8080", NuevoDTO.class);

	    if (responseEntity.getStatusCode()==HttpStatus.OK)
	        return (List<NuevoDTO>) responseEntity.getBody();
	    throw new RuntimeException("El servidor no responde");
	}

}
