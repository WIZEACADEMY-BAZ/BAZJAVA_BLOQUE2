package com.wizeline.gradle.learningjavagradle.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.wizeline.gradle.learningjavagradle.batch.UserJob;

@WebMvcTest(BatchController.class)
class BatchControllerTest {
	
	private static final Logger LOGGER = Logger.getLogger(BatchControllerTest.class.getName());
	
	@MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private UserJob userJob;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testStartBatch() throws Exception {
		LOGGER.info("Iniciando Job de Batch");
		Job job =null;
		org.springframework.batch.core.JobExecution jobExecution=null;
		Mockito.when(userJob.printUsersJob()).thenReturn(job);
		Mockito.when(jobLauncher.run(Mockito.any(), Mockito.any())).thenReturn(jobExecution);
		mockMvc.perform(
			      get("/batch/start")
			      .accept(MediaType.APPLICATION_JSON_VALUE))
			     .andExpect(status().isOk())
			     .andReturn();
	}

}
