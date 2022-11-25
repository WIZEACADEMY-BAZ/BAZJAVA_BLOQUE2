package baz.practice.wizeline.learningjavamaven.service;

import baz.practice.wizeline.learningjavamaven.enums.Country;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BankAccountBOImplTest {

    public static final Logger LOGGER = Logger.getLogger(BankAccountBOImplTest.class.getName());

    @Mock
    BankingAccountRepository bankingAccountRepositoryTest;

    @InjectMocks
    BankAccountBOImpl bankAccountBOTest;

    @Mock
    MongoTemplate mongoTemplate;

    @Test
    void getAccountDetails() {
        LOGGER.info("Se incializa proceso TEST para getAccountDetails...");
        BankAccountDTO response=new BankAccountDTO();

        response = bankAccountBOTest.getAccountDetails("Ana","21-11-2022");
        assertEquals(response, response);

    }

    @Test
    void testGetAccountDetails() {
        LOGGER.info("Se incializa proceso TEST para testGetAccountDetails...");
        BankAccountDTO response=new BankAccountDTO();

        response = bankAccountBOTest.getAccountDetails("21-11-2022");
        assertEquals(response, response);
    }

    @Test
    void getAccounts() {
        LOGGER.info("Se incializa proceso TEST para getAccounts...");
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = bankAccountBOTest.buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList = bankAccountBOTest.getAccounts();

        verify(mongoTemplate,times(1)).save(bankAccountOne);
        verify(mongoTemplate,times(1)).findAll(BankAccountDTO.class);
    }

    @Test
    void updateAccount() {
        LOGGER.info("Se incializa proceso TEST para updateAccount...");
        Query query = new Query();
        query.addCriteria(Criteria.where("accountName").is("Ana"));

        Update update = new Update();
        update.set("accountName", "Anita");

        bankAccountBOTest.updateAccount("Ana", "Anita");
        verify(mongoTemplate,times(1)).updateFirst(query, update, BankAccountDTO.class);
    }

    @Test
    void deleteAccounts() {
        LOGGER.info("Se incializa proceso TEST para deleteAccounts...");
        bankAccountBOTest.deleteAccounts();
        verify(bankingAccountRepositoryTest,times(1)).deleteAll();
    }

    @Test
    void getAccountByUser() {
        LOGGER.info("Se incializa proceso TEST para getAccountByUser...");
        String user = "Ana";
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is("Ana"));
        List<BankAccountDTO> response = bankAccountBOTest.getAccountByUser(user);
        verify(mongoTemplate,times(1)).find(query, BankAccountDTO.class);

    }

    @Test
    void getAccountByName() {
        LOGGER.info("Se incializa proceso TEST para getAccountByName...");
        String user = "Ana";
        Query query = new Query();
        BankAccountDTO response= new BankAccountDTO();
        query.addCriteria(Criteria.where("accountName").is("Ana"));
        response = bankAccountBOTest.getAccountByName(user);
        verify(mongoTemplate,times(1)).findOne(query, BankAccountDTO.class);
    }
}