package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.batch.UserJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BatchControllerTest {

  // Revisión: Uso de Mockito en cada prueba
  @Mock
  JobLauncher jobLauncherMock;
  @Mock
  UserJob userJobMock;
  @Mock
  Job jobMock;
  @Mock
  JobParameters jobParametersMock;
  @Mock
  JobExecution jobExecutionMock;

  // Revisión: Prueba unitaria de cada endpoint de la API
  @InjectMocks
  private BatchController batchController;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

    when(userJobMock.printUsersJob()).thenReturn(jobMock);

    when(jobLauncherMock.run(jobMock,jobParametersMock)).thenReturn(jobExecutionMock);

    assertNotNull(batchController.startBatch());
  }
}