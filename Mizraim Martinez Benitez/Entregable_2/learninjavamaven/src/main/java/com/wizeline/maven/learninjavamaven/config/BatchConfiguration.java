package com.wizeline.maven.learninjavamaven.config;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private BankAccountJob bankAccountJob;

    @Autowired
    private JobLauncher jobLauncher;

    /**
     * Ejecuta el job de bankAccount cada 15 segundos, agrega un par de parametros al job.
     * @throws Exception
     */
    @Scheduled(fixedRate = 15000)
    public void scheduledByFixedRate() throws Exception{
        SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.S");
        LOGGER.info("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("launchDate", format.format(Calendar.getInstance().getTime()))
                .addString("project", "LearningJava")
                .toJobParameters();
        jobLauncher.run(bankAccountJob.bankAccountsBackupJob(), jobParameters);
        LOGGER.info("Batch job executed successfully");
    }
}
