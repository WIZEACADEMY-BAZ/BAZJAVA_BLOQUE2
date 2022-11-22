package com.wizeline.baz.LearningSpring.controller;

import com.wizeline.baz.LearningSpring.patron.creational.ResponseDTO;
import com.wizeline.baz.LearningSpring.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.logging.Logger;


@WebMvcTest(BatchController.class)
class BatchControllerTest {

    private static final Logger LOGGER = Logger.getLogger(KafkaControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobLauncher jobLauncher;

    @MockBean
    Job job;


    @Test
    void testBatchSpring() throws Exception {
        LOGGER.info("Prueba de Controller Spring Batch");
        org.springframework.batch.core.JobExecution jobExecution=null;
        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);
        mockMvc.perform(get("/api2/batch")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }
}