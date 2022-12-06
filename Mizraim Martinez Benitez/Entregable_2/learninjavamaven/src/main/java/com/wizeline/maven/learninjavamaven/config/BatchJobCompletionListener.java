package com.wizeline.maven.learninjavamaven.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Listen the job that have finished its execution
 */
public class BatchJobCompletionListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchJobCompletionListener.class);

    /**
     * Escucha cuando un job ha terminado su ejecucion y comprueba su estado
     * @param jobExecution es el job que termino su ejecucion.
     */
    @Override
    public void afterJob(JobExecution jobExecution){
        if (jobExecution.getStatus() == BatchStatus.COMPLETED){
            LOGGER.info("Batch job: {} with id: {} completed with status: {}",
                    jobExecution.getJobInstance().getJobName(), jobExecution.getJobId(), jobExecution.getStatus());
        }
    }
}
