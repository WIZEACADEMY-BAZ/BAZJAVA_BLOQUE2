package com.wizeline.baz.LearningSpring.controller;

import com.wizeline.baz.LearningSpring.model.BankAccountDTO;
import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.service.BankAccountService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankingAccountController.class)
class BankingAccountControllerTest {

    private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankAccountService service;

    @MockBean
    private CommonServices commonServices;

    @Test
    void getUserAccountTest() throws Exception {

        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        ResponseDTO responseDTO = new ResponseDTO();
        when(service.getAccountDetails(anyString(), anyString())).thenReturn(bankAccountDTO);
        when(commonServices.login(anyString(), anyString())).thenReturn(responseDTO);
        mockMvc.perform(get("/api/getUserAccount").param("user", "Erick").param("password", "12345").param("date", "201122").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAccountsTest() throws Exception {
        List<BankAccountDTO> list = new ArrayList<>();
        when(service.getAccounts()).thenReturn(list);
        mockMvc.perform(get("/api/getAccounts")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAccountByUserTest() throws Exception {
        List<BankAccountDTO> list = new ArrayList<>();
        when(service.getAccountByUser(anyString())).thenReturn(list);
        mockMvc.perform(get("/api/getAccountByUser")
                        .param("user",anyString())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAccountsGroupByTypeTest() throws Exception {
        List<BankAccountDTO> list = new ArrayList<>();
        when(service.getAccounts()).thenReturn(list);
        mockMvc.perform(get("/api/getAccountsGroupByType")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void deleteAccountsTest() throws Exception {

       doNothing().when(service).deleteAccounts();
        mockMvc.perform(delete("/api/deleteAccounts")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void sayHelloTest() throws Exception {
        mockMvc.perform(get("/api/sayHello")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAccountDetailsTest() throws Exception {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        when(service.getAccountDetails(anyString(),anyString())).thenReturn(bankAccountDTO);

        mockMvc.perform(get("/api/getAccountDetails")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }
}