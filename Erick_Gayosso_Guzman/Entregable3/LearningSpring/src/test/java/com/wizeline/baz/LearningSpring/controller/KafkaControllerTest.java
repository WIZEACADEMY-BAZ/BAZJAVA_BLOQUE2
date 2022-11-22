package com.wizeline.baz.LearningSpring.controller;

import com.wizeline.baz.LearningSpring.patron.creational.ResponseDTO;
import com.wizeline.baz.LearningSpring.service.KafkaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.logging.Logger;

@WebMvcTest(KafkaController.class)
class KafkaControllerTest {

    private static final Logger LOGGER = Logger.getLogger(KafkaControllerTest.class.getName());

    @MockBean
    private KafkaService service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testProducerKafka() throws Exception {
        LOGGER.info("Prueba de Controller Kafka");
        ResponseDTO responseDTO = new ResponseDTO.ResponseDTOBuilder("OK000","SUCCESS").build();
        when(service.producerKafka(anyString())).thenReturn(responseDTO);
        mockMvc.perform(get("/api2/kafkaProducer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("input","test"))
                .andExpect(status().isOk()).andReturn();
    }
}