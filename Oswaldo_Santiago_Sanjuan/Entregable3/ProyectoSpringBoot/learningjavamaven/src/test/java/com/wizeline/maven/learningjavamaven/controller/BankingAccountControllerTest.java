package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.batch.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.chainofresponsibility.MoneyChainHandler;
import com.wizeline.maven.learningjavamaven.model.Account;
import com.wizeline.maven.learningjavamaven.model.Post;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;


import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankingAccountControllerTest {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    @Mock

    AccountsJSONClient accountsJSONClient;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private KafkaTemplate<Object, Object> template;

    @InjectMocks
    private BankingAccountController bankingAccountController;

    @Test
    void getAccounts() {
        //Prepara el esenario de la prueba
        LOGGER.info("Entrando a realizar la preuba");
        //Ejecutar la logica a probar
         bankingAccountController.getAccounts();
        //llamadas
        verify(bankAccountService,times(1)).getAccounts();
    }

    @Test
    void factory() {
    //Prepara el esenario de la prueba
        LOGGER.info("Entrando a realizar la prueba. ");
        //Ejecuta la logica a probar
        ResponseEntity<List<Account>> r =   bankingAccountController.Factory();
        //llamdas
        assertTrue(r.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");

    }

    @Test
    void responsibility() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la preuba ");
        Integer cantidad = 2;
        //Ejecuta la logica
        ResponseEntity<List<MoneyChainHandler>> p = bankingAccountController.responsibility(cantidad);
        //llamadas
        assertTrue(p.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
    }

    @Test
    void deleteAccounts() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la preuba . . . . ");
        //Ejecuta la logica
        ResponseEntity e = bankingAccountController.deleteAccounts();
        //llamadas
        assertTrue(e.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
    }

    @Test
    void getAccountByUser() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la prueba . . . ");
        String user = "oswaldo";
        ResponseEntity b = bankingAccountController.getAccountByUser(user);
        //llamdas
        assertTrue(b.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");

    }

    @Test
    void getAccountsGroupByType() {
        try {
            ResponseEntity r = bankingAccountController.getAccountsGroupByType();
            //assertNotNull(r);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sayHelloGuest() {
        //Prepara el esenario de la prueba
        LOGGER.info("Entrendo al esenario de la prueba. . . ");
        //Ejecuta la logica
        ResponseEntity s = bankingAccountController.sayHelloGuest();
        assertTrue(s.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");

    }

    @Test
    void getExternalUser() {
        //Prepara el esenario de la prueba
        LOGGER.info("Entrando a la prueba ");
        //Ejecuta la logica
        Post p = new Post();
        Long userId = 2L;
        when(accountsJSONClient.getPostById(any())).thenReturn(p);
        ResponseEntity<Post> resul = bankingAccountController.getExternalUser(userId);
        assertTrue(resul.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");


    }

    @Test
    void sendUserAccount() {
    }

    @Test
    void getAccountByAccountNumber() {
    }

    @Test
    void putCountry() {
    }
}