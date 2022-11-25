package com.wizeline.maven.learningjavamaven.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.chainofresponsibility.MoneyChainHandler;
import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.Account;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
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
import java.util.Optional;
import java.util.logging.Logger;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {

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
        assertTrue(r.getStatusCode().is2xxSuccessful());

    }

    @Test
    void responsibility() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la preuba ");
        Integer cantidad = 2;
        //Ejecuta la logica
        ResponseEntity<List<MoneyChainHandler>> p = bankingAccountController.responsibility(cantidad);
        //llamadas
        assertTrue(p.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteAccounts() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la preuba . . . . ");
        //Ejecuta la logica
        ResponseEntity e = bankingAccountController.deleteAccounts();
        //llamadas
        assertTrue(e.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getAccountByUser() {
        //Prepara el esenario de la prueba
        LOGGER.info("Preparando la prueba . . . ");
        String user = "oswaldo";
        ResponseEntity b = bankingAccountController.getAccountByUser(user);
        //llamdas
        assertTrue(b.getStatusCode().is2xxSuccessful());

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
        assertTrue(s.getStatusCode().is2xxSuccessful());

    }

    @Test
    void getExternalUser() {
        //Prepara el esenario de la prueba
        LOGGER.info("Entrando a la prueba ");
        Post p = new Post();
        Long userId = 2L;
        //Ejecuta la logica
        when(accountsJSONClient.getPostById(any())).thenReturn(p);
        ResponseEntity<Post> resul = bankingAccountController.getExternalUser(userId);
        assertTrue(resul.getStatusCode().is2xxSuccessful());


    }

    @Test
    void sendUserAccount() {
        //Haca si no puedo necesito hayuda
        Integer userId = 2;
        bankingAccountController.sendUserAccount(userId);
        //Preparo el esenario
        LOGGER.info("Preparo el metodo");

    }

    @Test
    void getAccountByAccountNumber() {
        //Preparo el esenario
        LOGGER.info("Preparo el metodo . . ");
        long accountNumber = 2;
        BankAccountDTO o = new BankAccountDTO();
        when(bankAccountService.getAccountByAccountNumber(accountNumber)).thenReturn(Optional.of(o));
        //Ejecuto la preuba
        ResponseEntity<BankAccountDTO>  a = bankingAccountController.getAccountByAccountNumber(accountNumber);
        //LLamadas
        assertTrue(a.getStatusCode().is2xxSuccessful());

    }

    @Test
    void putCountry() {
        //Preparo el esenario de prueba
        LOGGER.info("Preparo el metodo. . . ");
        BankAccountDTO m = new BankAccountDTO();
        String country = "mexico";
        when(bankAccountService.putCountry(country)).thenReturn(m);
        //Ejecuto la logica
        ResponseEntity<BankAccountDTO> l = bankingAccountController.putCountry(country);
        //Realizo la llamad a
        assertTrue(l.getStatusCode().is2xxSuccessful());


    }
}
