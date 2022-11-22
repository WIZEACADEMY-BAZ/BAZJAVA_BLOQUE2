package com.wizeline.gradle.learningjavagradle.configuration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.wizeline.gradle.learningjavagradle.batch.BankAccountJob;

@SpringBootTest(classes = BatchConfigurationTest.class)
@WebMvcTest(BatchConfiguration.class)
class BatchConfigurationTest {

	private static final Logger LOGGER = Logger.getLogger(BatchConfigurationTest.class.getName());
	
	@MockBean
	private BankAccountJob bankAccountJob;

	@MockBean
	private JobLauncher jobLauncher;

	@Autowired
	BatchConfiguration config;
	
	 @Test
	void testScheduledByFixedRate() throws Exception {
		 LOGGER.info("prueba de configuracion Batch");
			Job job =null;
			org.springframework.batch.core.JobExecution jobExecution=null;
			Mockito.when(bankAccountJob.bankAccountsBackupJob()).thenReturn(job);
			Mockito.when(jobLauncher.run(Mockito.any(), Mockito.any())).thenReturn(jobExecution);
			config.scheduledByFixedRate();
	}

}
