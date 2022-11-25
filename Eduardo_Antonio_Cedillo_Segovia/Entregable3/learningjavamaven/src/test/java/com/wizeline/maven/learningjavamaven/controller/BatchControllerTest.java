package com.wizeline.maven.learningjavamaven.controller;


import com.wizeline.maven.learningjavamaven.batch.UserJob;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

import static com.mongodb.assertions.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class BatchControllerTest {

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
