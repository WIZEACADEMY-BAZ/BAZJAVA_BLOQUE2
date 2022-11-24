package com.wizeline.gradle.learningjavagradle.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.logging.Logger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.wizeline.gradle.learningjavagradle.batch.UserJob;

@WebMvcTest(BatchController.class)
class BatchControllerTest {
	private static final Logger LOGGER = Logger.getLogger(BatchController.class.getName());
	
	@MockBean
    private JobLauncher jobLauncher;

    @MockBean
    private UserJob userJob;
    
	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("Batch start")
	void test() throws Exception {
		LOGGER.info("Iniciando Job de Batch desde el controller");
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
