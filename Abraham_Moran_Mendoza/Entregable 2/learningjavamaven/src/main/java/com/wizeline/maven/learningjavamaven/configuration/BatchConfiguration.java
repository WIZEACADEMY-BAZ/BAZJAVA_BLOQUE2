package com.wizeline.maven.learningjavamaven.configuration;

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

  // Inyectar Job y JobLauncher
  @Autowired
  JobLauncher jobLauncher;
  @Autowired
  BankAccountJob bankAccountJob;

  private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfiguration.class);

  /**
   * Ejecuta el job de bankAccount cada 15 segundos, agrega un par de parámetros al job.
   * @throws Exception
   */
  @Scheduled(fixedRate = 15000)
  public void scheduledByFixedRate() throws Exception {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    LOGGER.info("Batch job starting");
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("launchDate", format.format(Calendar.getInstance().getTime()))
        .addString("project", "LearningJava")
        .toJobParameters();
    jobLauncher.run(bankAccountJob.bankAccountsBackupJob(), jobParameters);
    LOGGER.info("Batch job executed successfully");
  }
}