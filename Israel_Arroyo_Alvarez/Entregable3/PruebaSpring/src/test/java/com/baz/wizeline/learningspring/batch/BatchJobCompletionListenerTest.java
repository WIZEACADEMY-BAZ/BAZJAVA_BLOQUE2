package com.baz.wizeline.learningspring.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BatchJobCompletionListenerTest {

    @InjectMocks
    BatchJobCompletionListener listener;

    @Test
    @DisplayName("Listener Bactch")
    void afterJob() {
        JobExecution jobExecution = new JobExecution(1L);
        jobExecution.setStatus(BatchStatus.ABANDONED);
        listener.afterJob(jobExecution);
    }
}