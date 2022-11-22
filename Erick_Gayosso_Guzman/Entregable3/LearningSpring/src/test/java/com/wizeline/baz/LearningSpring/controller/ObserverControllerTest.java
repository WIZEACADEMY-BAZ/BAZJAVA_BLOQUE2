package com.wizeline.baz.LearningSpring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ObserverController.class)
class ObserverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void sendMessageTest() throws Exception {
        mockMvc.perform(get("/api2/chat/sendMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("messagge", "LEIDO"))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    void readMessageTest() throws Exception {
        mockMvc.perform(get("/api2/chat/readMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("messagge", "LEIDO"))
                .andExpect(status().isOk()).andReturn();
    }
}