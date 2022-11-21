package baz.practice.wizeline.learningjavamaven.service;

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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BankAccountBOImplTest {

    @Mock
    BankingAccountRepository bankingAccountRepositoryTest;

    @InjectMocks
    BankAccountBOImpl bankAccountBOTest;

    @Mock
    MongoTemplate mongoTemplate;

    @Test
    void getAccountDetails() {
    }

    @Test
    void testGetAccountDetails() {
    }

    @Test
    void getAccounts() {
    }

    @Test
    void updateAccount() {

        Query query = new Query();
        query.addCriteria(Criteria.where("accountName").is("Ana"));

        Update update = new Update();
        update.set("accountName", "Anita");

        bankAccountBOTest.updateAccount("Ana", "Anita");
        verify(mongoTemplate,times(1)).updateFirst(query, update, BankAccountDTO.class);
    }

    @Test
    void deleteAccounts() {
        bankAccountBOTest.deleteAccounts();
        verify(bankingAccountRepositoryTest,times(1)).deleteAll();
    }

    @Test
    void getAccountByUser() {
    }

    @Test
    void getAccountByName() {
    }
}