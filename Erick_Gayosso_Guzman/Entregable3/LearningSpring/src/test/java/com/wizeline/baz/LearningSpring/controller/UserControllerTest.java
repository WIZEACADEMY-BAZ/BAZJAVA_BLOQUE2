package com.wizeline.baz.LearningSpring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.model.UserDTO;
import com.wizeline.baz.LearningSpring.service.UserService;
import com.wizeline.baz.LearningSpring.utils.CommonServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final Logger LOGGER = Logger.getLogger(UserControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;
    @MockBean
    private CommonServices commonServices;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginTest() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        when(commonServices.login(anyString(), anyString())).thenReturn(responseDTO);
        when(userService.login(anyString(), anyString())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/login")
                .param("user", "Erick")
                .param("password", "12345")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createUserTest() throws Exception {

        UserDTO userDTO = new UserDTO();
        userDTO.setUser("Erick");
        userDTO.setPassword("12345");
        String usuario = objectMapper.writeValueAsString(userDTO);
        ResponseDTO response =new ResponseDTO();
        when(userService.createUser("Erick","12345")).thenReturn(response);
        mockMvc.perform(post("/api/createUser")
                        .content(usuario)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void createUsersTest() throws Exception {
        List<UserDTO> userDTOList = new ArrayList<>();
        String usuario = objectMapper.writeValueAsString(userDTOList);
        ResponseDTO response =new ResponseDTO();
        when(userService.createUser("Erick","12345")).thenReturn(response);
        mockMvc.perform(post("/api/createUsers")
                        .content(usuario)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();
    }
}