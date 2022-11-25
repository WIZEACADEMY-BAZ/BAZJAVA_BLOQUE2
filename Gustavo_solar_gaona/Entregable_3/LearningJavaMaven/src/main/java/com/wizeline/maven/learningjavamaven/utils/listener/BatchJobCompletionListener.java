package com.wizeline.maven.learningjavamaven.utils.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class BatchJobCompletionListener extends JobExecutionListenerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobCompletionListener.class);

    public void afterJob(JobExecution jobExecution){
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            LOGGER.info("Batch Job: {} with id {}", jobExecution.getJobInstance(), jobExecution.getJobId());
        }
    }
}
