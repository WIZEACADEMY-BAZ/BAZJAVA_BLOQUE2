package com.baz.wizeline.learningspring.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.baz.wizeline.learningspring.batch.BankAccountJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private BankAccountJob bankAccountJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(fixedRate = 3000000)
    public void scheduledByFixedRate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        LOGGER.info("Inicia Batch");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("launchDate", format.format(Calendar.getInstance().getTime()))
                .addString("project", "PruebaSpring")
                .toJobParameters();
        jobLauncher.run(bankAccountJob.bankAccountsBackupJob(), jobParameters);
        LOGGER.info("Batch terminado");
    }


    @Scheduled(fixedRate = 1500000)
    public void scheduledByUserMexico() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        LOGGER.info("Inicia Batch Mexico");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("launchDate", format.format(Calendar.getInstance().getTime()))
                .addString("project", "PruebaSpring")
                .toJobParameters();
        jobLauncher.run(bankAccountJob.bankAccountsMexicoJob(), jobParameters);
        LOGGER.info("Batch Mexico terminado");
    }



}