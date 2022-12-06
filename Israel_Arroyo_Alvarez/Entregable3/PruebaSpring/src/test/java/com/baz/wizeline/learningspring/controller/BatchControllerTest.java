package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.batch.UserJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.job.flow.FlowJob;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchControllerTest {

    @Mock
    JobLauncher jobLauncher;

    @Mock
    UserJob userJob;

    @InjectMocks
    BatchController controller;

    Job job;
    JobExecution execution;

    @BeforeEach
    void setUp() {
        job = new FlowJob("FlowJob");
        execution = new JobExecution(1L);
    }

    @Test
    @DisplayName("Batch")
    void startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        when(userJob.printUsersJob()).thenReturn(job);
        when(jobLauncher.run(any(), any())).thenReturn(execution);
        ResponseEntity<String> response = controller.startBatch();
        assertNotNull(response);
    }


}