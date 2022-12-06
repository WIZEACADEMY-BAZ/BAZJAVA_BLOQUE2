package com.wizeline.maven.learningjavagmaven.service;

import com.wizeline.maven.learningjavagmaven.LearningjavagmavenApplication;
import com.wizeline.maven.learningjavagmaven.controller.BankingAccountController;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.utils.CommonServices;
import  com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());

    @InjectMocks
    private BankingAccountController bankingController;

    @Mock
    private CommonServices BankingAccountModel;

    @Mock
    private BankAccountService bankAccountService;



    @Test
    void getAccountDetails() {
        LOGGER.info("Se inicia TEST de get Account Details");
        BankAccountModel response=new BankAccountModel();

        response=BankAccountService.getAccountDetails("Ana","22-10-22");
        assertEquals(response,response);
    }


}