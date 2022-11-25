package com.wizeline.maven.learninjavamaven.controller;

import com.wizeline.maven.learninjavamaven.batch.UserJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BatchControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchControllerTest.class);

    @InjectMocks
    private BatchController controllerTest;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private UserJob userJob;

    @Test
    void startBatchTest() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException,
            JobParametersInvalidException, JobRestartException {
        LOGGER.info("startBatchTest");
        Job job = new FlowJob("nameTest");
        JobExecution execution = new JobExecution(1L);
        when(userJob.printUsersJob()).thenReturn(job);
        when(jobLauncher.run(any(), any())).thenReturn(execution);
        assertAll(
                () -> assertNotNull(controllerTest.startBatch()),
                () -> assertEquals(controllerTest.startBatch().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

}
