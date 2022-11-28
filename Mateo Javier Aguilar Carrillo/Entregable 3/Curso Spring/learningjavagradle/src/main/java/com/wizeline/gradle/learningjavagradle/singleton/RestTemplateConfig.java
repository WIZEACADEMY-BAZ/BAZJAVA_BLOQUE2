package com.wizeline.gradle.learningjavagradle.singleton;

import java.util.logging.Logger;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.gradle.learningjavagradle.model.RandomPassword;

public class RestTemplateConfig {
	public String value;
	private static final Logger LOGGER = Logger.getLogger(RestTemplateConfig.class.getName());
	
	
	private RestTemplateConfig() {
	}
	
	public static RestTemplateConfig getInstance() {
		return RestTemplateHolder.INSTANCE;
	}
	
	public RandomPassword getRandomPassword() {
		LOGGER.info("Conectando al generador de contraseñas...");
		RandomPassword password = new RandomPassword();
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();

		try {
			ResponseEntity<String> entity = restTemplate.exchange("https://www.passwordrandom.com/query?command=password", HttpMethod.GET, null, String.class);
			if(entity.hasBody()) {
				LOGGER.info("Contraseña generada: " + entity.getBody());
				password = mapper.readValue(entity.getBody(), RandomPassword.class);
			}

		} catch (RestClientException | JsonProcessingException e) {
			LOGGER.info("Ocurrió un error al consultar el API: " + e.getMessage());
		}
		
		return password;
	}
	
	public void connect() {
		
	}
	
	private static class RestTemplateHolder {
		private static final RestTemplateConfig INSTANCE = new RestTemplateConfig();
	}

}
