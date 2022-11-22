package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.batch.UserJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchControllerTest {
    private static final Logger LOGGER = Logger.getLogger(BatchControllerTest.class.getName());

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private UserJob userJob;


    @InjectMocks
    BatchController batchController;

    @Test
    void startBatch() {
    LOGGER.info("Entrando a realizar la prueba. . . . ");
        ResponseEntity<String> batch =  batchController.startBatch();
        assertNotNull(batch);
        LOGGER.info("Terminado de realizar las prubas del metodo startBatch ");
    }
}



