package com.wizeline.gradle.learningjavagradle.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.wizeline.gradle.learningjavagradle.controller.BatchController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BatchTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchTests.class);
	@Mock
	UserJob userJob;
	@Mock
	JobLauncher jobLauncher;

	@InjectMocks
	BatchController batchController;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void batchTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
		LOGGER.info("batch Testing...");

		Job job = new FlowJob("jobTest");
		JobExecution jobExecution = new JobExecution(1L);

		when (userJob.printUsersJob()).thenReturn(job);

		when(jobLauncher.run(any(), any())).thenReturn(jobExecution);

		assertNotNull(batchController.startBatch());
		assertEquals(batchController.startBatch().getStatusCode().value(), HttpStatus.OK.value());
	}

	@Test
	public void readerTest() throws Exception {
		LOGGER.info("reader Testing...");
		UserReader reader = new UserReader();

		assertNotNull(reader.read());
	}

	@Test
	public void writerTest() throws Exception {
		LOGGER.info("writer Testing...");
		UserWriter writer = new UserWriter();
		List<String> list = new ArrayList<>();
		list.add("ABC");
		list.add("DEF");

		writer.write(list);
	}
}
