package com.wizeline.entregabledavid.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.wizeline.entregabledavid.enums.AccountType;
import com.wizeline.entregabledavid.model.BankAccountDTO;
import com.wizeline.entregabledavid.service.BankAccountService;
import com.wizeline.entregabledavid.utils.CommonServices;

import java.time.LocalDateTime;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BankingAccountController.class})
@ExtendWith(SpringExtension.class)
class BankingAccountControllerTest {
    @MockBean
    private BankAccountService bankAccountService;

    @Autowired
    private BankingAccountController bankingAccountController;

    @MockBean
    private CommonServices commonServices;

    @MockBean
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * Metodo Test: {@link BankingAccountController#getAccounts()}
     */
    @Test
    void testGetAccounts() throws Exception {
        when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/getAccounts");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#getAccounts()}
     */
    @Test
    void testGetAccounts2() throws Exception {
        when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/getAccounts");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#deleteAccounts()}
     */
    @Test
    void testDeleteAccounts() throws Exception {
        doNothing().when(bankAccountService).deleteAccounts();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/deleteAccounts");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("All accounts deleted"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#deleteAccounts()}
     */
    @Test
    void testDeleteAccounts2() throws Exception {
        doNothing().when(bankAccountService).deleteAccounts();
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/deleteAccounts");
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("All accounts deleted"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#putAccountByUser(String)}
     */
    @Test
    void testPutAccountByUser() throws Exception {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountActive(true);
        bankAccountDTO.setAccountBalance(10.0d);
        bankAccountDTO.setAccountBalanceCifrado("3");
        bankAccountDTO.setAccountName("Juan");
        bankAccountDTO.setAccountNumber(1234567890L);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("US");
        bankAccountDTO.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setUser("User");
        when(bankAccountService.putAccountByUser((String) any())).thenReturn(bankAccountDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/putAccountByUser")
                .param("user", "values");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#getAccountsByUser(String)}
     */
    @Test
    void testGetAccountsByUser() throws Exception {
        when(bankAccountService.getAccountByUser((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/getAccountsByUser")
                .param("user", "values");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#sayHelloGuest()}
     */
    @Test
    void testSayHelloGuest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/sayHello");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Hola invitado!!"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#sayHelloGuest()}
     */
    @Test
    void testSayHelloGuest2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/sayHello");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Hola invitado!!"));
    }

    /**
     * Metodo Test: {@link BankingAccountController#sendUserAccount(Integer)}
     */
    @Test
    void testSendUserAccount() throws Exception {
        when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/send/{userId}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(bankingAccountController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

