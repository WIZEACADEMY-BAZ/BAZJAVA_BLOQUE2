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
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImplTest.class.getName());

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
        LOGGER.info("Entra prueba getAccountDetails ");
        BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails("eduardo","23-11-2022");
        LOGGER.info("bankAccountDTO"+bankAccountDTO);
        assertNotNull(bankAccountDTO);
    }

    @Test
    void testGetAccountDetails() {
        LOGGER.info("Entra prueba testGetAccountDetails. ");
        BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails("eduardo");
        LOGGER.info("bankAccountDTO"+bankAccountDTO);
        assertNotNull(bankAccountDTO);
    }


    @Test
    void getAccounts() {
        LOGGER.info("Entra prueba getAccounts");
        List<BankAccountDTO> bankAccountDTO = bankAccountService.getAccounts();
        LOGGER.info("bankAccountDTO"+bankAccountDTO);
        assertNotNull(bankAccountDTO);
    }

    @Test
    void deleteAccounts() {
        LOGGER.info("Entra prueba deleteAccounts");
        bankAccountService.deleteAccounts();
        verify(bankAccountRepository,times(1)).deleteAll();
        LOGGER.info("Prueba pasada correctamete ... ");
    }

    @Test
    void getAccountByUser() {
        LOGGER.info("Entra prueba getAccountByUser ");
        List<BankAccountDTO> bankAccountDTO = bankAccountService.getAccountByUser("eduardo");
        LOGGER.info("bankAccountDTO"+bankAccountDTO);
        assertNotNull(bankAccountDTO);
    }

    @Test
    void getAccountByAccountNumber() {
        LOGGER.info("Entrando a realizar las pruebas. ");
        Optional<BankAccountDTO> bankAccountDTOS = bankAccountService.getAccountByAccountNumber(123);
        assertNotNull(bankAccountDTOS);
        LOGGER.info("Prueba correctamente...");
    }


    //Pregunta por este metodo
    @Test
    void putCountry() {
        LOGGER.info("Entrando a realizar la edicion . . . ");
        BankAccountDTO bankAccountDTO = bankAccountService.putCountry("Ciudad de Mexico");
        Query query = new Query();
        query.addCriteria(Criteria.where("country").is("country"));
        Update update = new Update();
        update.set("country","Ciudad de Mexico");
        verify(mongoTemplate,times(1)).updateFirst(query,update, BankAccountDTO.class);
        LOGGER.info("Pruebas de actualizacion correctamente...");
    }
}
