package com.wizeline.maven.learningjavagmaven.controller;

import com.wizeline.maven.learningjavagmaven.LearningjavagmavenApplication;
import com.wizeline.maven.learningjavagmaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavagmaven.configuration.KafkaConfiguration;
import com.wizeline.maven.learningjavagmaven.enums.AccountType;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.model.Post;
import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import com.wizeline.maven.learningjavagmaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavagmaven.utils.CommonServices;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.maven.learningjavagmaven.controller.BankingAccountController;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.meta.When;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {
    private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());


    @InjectMocks
    private BankingAccountController bankingController;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CommonServices commonServices;

    @Mock
    private Util util;

    @Mock
    private KafkaConfiguration kafkaConfiguration;

    @Mock
    private AccountsJSONClient accountsJSONClient;




    @Test
    void getExternalUser() {
        LOGGER.info("Inicia Test de getExternalUser");
        Post post = new Post();
        when(accountsJSONClient.getPostById(1L)).thenReturn(post);
        ResponseEntity<Post> responseEntity = bankingController.getExternalUser(1L);
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(post, responseEntity.getBody())
        );
    }

    @Test
    void sayHelloGuest() {
        LOGGER.info("Inicia prueba de sayHelloGuest");
        ResponseEntity<String> httpResponse = bankingController.sayHelloGuest();
        assertEquals("Hola invitado!!", httpResponse.getBody());
    }

    @Test
    void sendUserAccountTest(){
        LOGGER.info("Inicia prueba de sendUserAccountTest");
        BankAccountModel bankAccountDTO = new BankAccountModel();
        List<BankAccountModel> accounts = new ArrayList<>();
        accounts.add(bankAccountDTO);
        when(bankAccountService.getAccounts()).thenReturn(accounts);
        bankingController.sendUserAccount(0);
        assertEquals(1, accounts.size());
    }


}
