package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountServiceImplTest {

    @Mock
    BankingAccountRepository bankAccountRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;



    @Test
    void getAccountDetails() {
        BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails("oswaldo","17-11-2022");
        assertNotNull(bankAccountDTO);
    }

    @Test
    void testGetAccountDetails() {
        BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails("oswaldito");
        assertNotNull(bankAccountDTO);
    }

    @Test
    void testGetAccountDetails1() {

    }

    @Test
    void getAccounts() {
        List<BankAccountDTO> bankAccountDTO = bankAccountService.getAccounts();
        assertNotNull(bankAccountDTO);
    }

    @Test
    void deleteAccounts() {
        bankAccountService.deleteAccounts();
        verify(bankAccountRepository,times(1)).deleteAll();
    }

    @Test
    void getAccountByUser() {
        List<BankAccountDTO> bankAccountDTO = bankAccountService.getAccountByUser("oswaldo");
        assertNotNull(bankAccountDTO);
    }

    @Test
    void getAccountByAccountNumber() {
        Optional<BankAccountDTO> bankAccountDTOS = bankAccountService.getAccountByAccountNumber(123);
        assertNotNull(bankAccountDTOS);
    }


    //Pregunta por este metodo
    @Test
    void putCountry() {
        BankAccountDTO bankAccountDTO = bankAccountService.putCountry("Ecatepec");
        Query query = new Query();
        query.addCriteria(Criteria.where("country").is("country"));
        Update update = new Update();
        update.set("country","Ecatepec");
        verify(mongoTemplate,times(1)).updateFirst(query,update, BankAccountDTO.class);

    }
}