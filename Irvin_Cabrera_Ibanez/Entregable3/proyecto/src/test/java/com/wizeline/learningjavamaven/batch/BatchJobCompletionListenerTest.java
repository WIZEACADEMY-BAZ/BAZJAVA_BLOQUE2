package com.wizeline.learningjavamaven.batch;

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
  BatchJobCompletionListener batchJobCompletionListener;

  @Test
  void afterJob() {
    JobExecution jobExecution = new JobExecution(1L);
    jobExecution.setStatus(BatchStatus.ABANDONED);
    batchJobCompletionListener.afterJob(jobExecution);
  }
}