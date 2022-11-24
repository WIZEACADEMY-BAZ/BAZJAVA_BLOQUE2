package com.wizeline.maven.learningjavagmaven.repository;

import com.wizeline.maven.learningjavagmaven.LearningjavagmavenApplication;
import com.wizeline.maven.learningjavagmaven.controller.BankingAccountController;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import com.wizeline.maven.learningjavagmaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavagmaven.enums.Country;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@ExtendWith(MockitoExtension.class)
public class RepositoryTest {

    private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());

    @InjectMocks
    BankAccountServiceImpl  bankAccountServiceTest;

    @InjectMocks
    private BankingAccountController bankingController;

    @Mock
    BankingAccountController bankingAccountController;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    MongoTemplate mongoTemplate;

    @Test
    void getAccountDetailsTest(){
        LOGGER.info("se inicia proceso getAccountDetails");
        BankAccountModel response = new BankAccountModel();
        response=bankAccountServiceTest.getAccountDetails("Ana", "01-10-2010");
        assertEquals(response,response);
    }

    @Test
    void getAccountsTest(){
        LOGGER.info("inicia Test de GetAccounts");
        List<BankAccountModel> accountModelList=new ArrayList<>();
        BankAccountModel bankAccountOne= bankAccountServiceTest.buildBankAccount("user1@wizeline.com",true,Country.MX);
        accountModelList= bankAccountServiceTest.getAccounts();
        verify(mongoTemplate,times(1)).findAll(BankAccountModel.class);
    }


    @Test
    void deleteAccountsTest() {
        ResponseEntity response= bankingController.deleteAccounts();
        assertEquals(response.getBody(),"All accounts deleted");
    }

    @Test
    public void getAccountByUserTest(){
     LOGGER.info("se inicia GetAccountByUserTest");

     String user ="Ana";
     Query query=new Query();
     query.addCriteria(Criteria.where("userName").is("Ana"));
     List<BankAccountModel> response =bankAccountServiceTest.getAccountByUser(user);
     verify(mongoTemplate,times(1)).find(query, BankAccountModel.class);
    }


    @Test
    void updateAccountTest(){
        LOGGER.info("Inici Test Update");
        Query query= new Query();
        query.addCriteria(Criteria.where("accountName").is("Ana"));
        Update update=new Update();
        update.set("accountName","Anita");
        bankAccountServiceTest.updateAccount("Ana","Anita");
        verify(mongoTemplate,times(1)).updateFirst(query,update,BankAccountModel.class);
    }

    @Test
    void getAccountByNameTest(){
        LOGGER.info("Inicia Test getAccountByNameTest");
        String user ="Juan";
        Query query=new Query();
        query.addCriteria(Criteria.where("userName").is("Juan"));
        List<BankAccountModel> response =bankAccountServiceTest.getAccountByUser(user);
        verify(mongoTemplate,times(1)).find(query, BankAccountModel.class);




    }


}
