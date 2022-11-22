package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import com.wizeline.maven.learningjavamaven.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
public class BankingAccountControllerTest {
    private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void sayHelloEndpointTest() throws Exception {
        String resultadoPeticion = mockMvc.perform(get("/api/sayHello"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LOGGER.info(resultadoPeticion);
        Assertions.assertEquals("Hola invitado!!", resultadoPeticion);
    }

    @Test
    void getAccountsTest() throws Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/api/getAccounts"))
                        .andExpect(status().isOk())
                        .andReturn();
        BankAccountModel[] accountsArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountModel[].class);
        List<BankAccountModel> accountsResultado = Arrays.asList(accountsArreglo);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertFalse(accountsResultado.stream()
                .map(BankAccountModel::getUserName)
                .collect(Collectors.toList())
                .containsAll(List.of("user1@wizeline.com","user2@wizeline.com","user4@wizeline.com"))
        );
    }

    @Test
    void getExternalUserTest() throws  Exception{
        MvcResult resultadoPeticion =
                mockMvc.perform(get("/api/getExternalUser/{userId}",7))
                        .andExpect(status().isOk())
                        .andReturn();

        Post postResultado = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), Post.class);
        LOGGER.info(postResultado.getBody());

        Assertions.assertEquals(7,postResultado.getId());

    }

    @Test
    void sendUserAccountTest() throws Exception{
        MvcResult resultadoPeticion = mockMvc.perform(post("/api/send/{userId}",2)
                .content(mapper.writeValueAsString(""))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.toString());
    }

    @Test
    void updateUserAccounts() throws Exception {
        MvcResult resultadoPeticion =
                mockMvc.perform(put("/api/updateUserAccounts")
                        .param("oldUser","user3@wizeline.com")
                        .param("newUser", "user1@wizeline.com")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
        BankAccountModel[] accountsArreglo = mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), BankAccountModel[].class);
        List<BankAccountModel> accountsResultado = Arrays.asList(accountsArreglo);

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertFalse(accountsResultado.stream()
                .map(BankAccountModel::getUserName)
                .collect(Collectors.toList())
                .containsAll(List.of("user3@wizeline.com"))
        );

    }

    @Test
    void deleteAccounts() throws Exception{

        MvcResult resultadoPeticion = mockMvc.perform(delete("/api/deleteAccounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();

        LOGGER.info(resultadoPeticion.getResponse().getContentAsString());

        Assertions.assertEquals("All accounts deleted",resultadoPeticion.getResponse().getContentAsString());

    }
}
