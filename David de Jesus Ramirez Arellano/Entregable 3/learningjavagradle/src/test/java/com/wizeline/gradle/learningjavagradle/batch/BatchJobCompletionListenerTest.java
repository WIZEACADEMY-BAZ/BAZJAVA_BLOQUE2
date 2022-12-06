package com.wizeline.gradle.learningjavagradle.batch;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(BatchJobCompletionListener.class)
class BatchJobCompletionListenerTest {
	private static final Logger LOGGER = Logger.getLogger(BatchJobCompletionListener.class.getName());
	
	@Test
	void BatchJobCompletionListener() {
		LOGGER.info("Prueba de BatchJobCompletionListener");
		BatchJobCompletionListener listener= new BatchJobCompletionListener();
		JobExecution job = new JobExecution((long) 1234567);
		listener.afterJob(job);
	}

}
