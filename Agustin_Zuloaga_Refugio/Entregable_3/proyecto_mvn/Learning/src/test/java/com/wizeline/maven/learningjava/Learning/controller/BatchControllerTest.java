package com.wizeline.maven.learningjava.Learning.controller;

import com.wizeline.maven.learningjava.Learning.batch.UserJob;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchControllerTest {

    @InjectMocks
    BatchController batchController;

    @Mock
    JobLauncher jobLauncher;

    @Mock
    UserJob userJob;

    @Test
    @DisplayName("Proceso batch")
    void startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Job job = new FlowJob("name");
        JobExecution execution = new JobExecution(1L);
        when(userJob.printUsersJob()).thenReturn(job);
        when(jobLauncher.run(Mockito.any(), Mockito.any())).thenReturn(execution);
        ResponseEntity<String> response = batchController.startBatch();
        assertNotNull(response);
    }
}