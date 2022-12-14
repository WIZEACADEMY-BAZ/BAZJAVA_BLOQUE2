package com.wizeline.maven.learninjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learninjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.model.Post;
import com.wizeline.maven.learninjavamaven.service.BankAccountService;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.wizeline.maven.learninjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learninjavamaven.utils.exceptions.ExceptionGenerica;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {

    private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());

    @InjectMocks
    private BankingAccountController bankingController;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private AccountsJSONClient accountsJSONClient;

    @Mock
    private KafkaTemplate<Object, Object> template;

    @Test
    void getUserAccountTest(){
        String user = "user";
        String password = "pass";
        String date = "10-12-2022";

        ResponseEntity<?> httpResponse = bankingController.getUserAccount(user, password, date);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getAccountByUserTest(){
        LOGGER.info("getAccountByUserTest");
        String user = "user";
        BankAccountDTO bankAccountDTO= new BankAccountDTO();
        List<BankAccountDTO> accounts = new ArrayList<>();
        accounts.add(bankAccountDTO);

        when(bankAccountService.getAccountByUser(user)).thenReturn(accounts);

        ResponseEntity<?> httpResponse = bankingController.getAccountByUser(user);

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void getAccountsGroupByTypeTest() throws JsonProcessingException {
        LOGGER.info("getAccountsGroupByTypeTest");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        when(bankAccountService.getAccounts()).thenReturn(accounts);

        ResponseEntity<?> httpResponse = bankingController.getAccountsGroupByType();

        assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void sayHelloGuestTest(){
        LOGGER.info("sayHelloGuestTest");
        ResponseEntity<String> httpResponse = bankingController.sayHelloGuest();

        assertEquals("Hola invitado!!", httpResponse.getBody());
    }

    @Test
    void updateBankAccountTest() throws ExceptionGenerica {
        LOGGER.info("updateBankAccountTest");
        BankAccountDTO bankAccountDTODetails = new BankAccountDTO();
        BankAccountDTO bankAccountDTO =  new BankAccountDTO();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        when(bankAccountService.findByUser(anyString())).thenReturn(bankAccountDTO);

        ResponseEntity<Optional<BankAccountDTO>> responseEntity =
                bankingController.updateBankAccount("user", bankAccountDTODetails);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

    }

    @Test
    void updateBankAccountNotFoundTest(){
        LOGGER.info("updateBankAccountNotFoundTest");
        BankAccountDTO bankAccountDTODetails = new BankAccountDTO();
        when(bankAccountService.findByUser(anyString())).thenReturn(null);

        ResponseEntity<Optional<BankAccountDTO>> responseEntity =
                bankingController.updateBankAccount("user",bankAccountDTODetails );

        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void sendUserAccountTest (){
        LOGGER.info("sendUserAccountTest");
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        List<BankAccountDTO> accounts = new ArrayList<>();
        accounts.add(bankAccountDTO);
        when(bankAccountService.getAccounts()).thenReturn(accounts);
        bankingController.sendUserAccount(0);
        assertEquals(1, accounts.size());
    }

    @Test
    void sobreCargaTest(){
        LOGGER.info("sobreCargaTest");
        LocalDateTime localDateTime = LocalDateTime.now();
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setUser("user");
        bankAccountDTO.setLastUsage(localDateTime);
        bankAccountDTO.setCountry(String.valueOf(Country.FR));

        when(bankAccountService.getAccountDetails("usuario", "lastUsage", Country.FR))
                .thenReturn(bankAccountDTO);

        bankingController.sobrecarga();

        assertNotNull(bankAccountDTO);
    }

    @Test
    void getExternalUserTest(){
        LOGGER.info("getExternalUserTest");
        Post post = new Post("userId", 1L, "title", "body");

        when(accountsJSONClient.getPostById(1L)).thenReturn(post);

        ResponseEntity<Post> responseEntity = bankingController.getExternalUser(1L);

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(post, responseEntity.getBody())
        );
    }

    @Test
    void encryptedAccountsTest(){
        LOGGER.info("encryptedAccountsTest");
        List<BankAccountDTO> accounts = new ArrayList<>();

        when(bankAccountService.encryptedAccounts()).thenReturn(accounts);

        ResponseEntity<List<BankAccountDTO>> responseEntity = bankingController.encryptedAccounts();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(accounts, responseEntity.getBody())
        );
    }

    @Test
    void deleteAccountsTest(){
        LOGGER.info("deleteAccountsTest");
        ResponseEntity responseEntity = bankingController.deleteAccounts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void createUsersThreadTest(){
        LOGGER.info("createUsersThreadTest");
        ResponseEntity responseEntity = bankingController.createUsersThread();
        assertNotNull(responseEntity);
    }

    @Test
    void getAccountsTest(){
        LOGGER.info("getAccountsTest");
        List<BankAccountDTO> responseEntity = new ArrayList<>();

        when(bankAccountService.getAccounts()).thenReturn(responseEntity);

        ResponseEntity<List<BankAccountDTO>> listaResponseEntity = bankingController.getAccounts();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, listaResponseEntity.getStatusCode()),
                () -> assertEquals(responseEntity, listaResponseEntity.getBody())
        );
    }

}
