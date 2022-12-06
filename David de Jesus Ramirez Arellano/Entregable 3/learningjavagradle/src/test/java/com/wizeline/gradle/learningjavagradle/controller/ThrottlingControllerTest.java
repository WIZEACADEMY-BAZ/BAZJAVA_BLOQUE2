package com.wizeline.gradle.learningjavagradle.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ThrottlingController.class)
public class ThrottlingControllerTest {
	private static final Logger LOGGER = Logger.getLogger(ThrottlingController.class.getName());
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("throttling")
	void throttlingCorrecto() throws Exception {
		LOGGER.info("prueba de peticion throttling desde controller");
		ThrottlingController tC = new ThrottlingController();
		mockMvc.perform(
	      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
	     .andExpect(status().isOk())
	     .andReturn();
	}
	
	@Test
	@DisplayName("throttling 5 eticiones")
	void throttlingIncorrecto() throws Exception {
		ThrottlingController tC = new ThrottlingController();
		LOGGER.info("prueba de maximo de peticiones throttling desde controller");
		mockMvc.perform(
			      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
		mockMvc.perform(
			      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
		mockMvc.perform(
			      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
		mockMvc.perform(
			      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
		mockMvc.perform(
			      get("/throttling").accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isTooManyRequests())
			     .andReturn();
        
	}
}
