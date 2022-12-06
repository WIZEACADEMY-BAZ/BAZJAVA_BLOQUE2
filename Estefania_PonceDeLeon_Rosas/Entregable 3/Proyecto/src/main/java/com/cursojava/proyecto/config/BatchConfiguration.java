package com.cursojava.proyecto.config;

import com.cursojava.proyecto.batch.EntrenadorItemProcessor;
import com.cursojava.proyecto.batch.EntrenadorBatchJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BatchConfiguration {

    @Autowired
    private EntrenadorBatchJob entrenadorJob;
    @Autowired
    private JobLauncher jobLauncher;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntrenadorItemProcessor.class);

    @Scheduled(fixedRate = 15000)
    public void scheduledByFixedRate() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        LOGGER.info("Batch job starting");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("launchDate", format.format(Calendar.getInstance().getTime()))
                .addString("project", "PokeJava API")
                .toJobParameters();
        jobLauncher.run(entrenadorJob.entrenadorBackupJob(), jobParameters);
        LOGGER.info("Batch job executed successfully");
    }
}