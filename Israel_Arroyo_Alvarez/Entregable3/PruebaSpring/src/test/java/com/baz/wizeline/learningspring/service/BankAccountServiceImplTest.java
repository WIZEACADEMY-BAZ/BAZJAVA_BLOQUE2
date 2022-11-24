package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.enums.Country;
import com.baz.wizeline.learningspring.model.BankAccountDTO;
import com.baz.wizeline.learningspring.repository.BankingAccountRepository;
import com.baz.wizeline.learningspring.utils.GenerateAccounts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    BankingAccountRepository bankingAccountRepository;

    @Mock
    MongoTemplate mongoTemplate;

    @InjectMocks
    BankAccountServiceImpl bankAccountService;

    List<BankAccountDTO> listBankAccounts;
    BankAccountDTO bankAccountDTO;
    String user;
    String lastUsage;



    @BeforeEach
    void setUp() {
        bankAccountDTO = new BankAccountDTO();
        listBankAccounts = new ArrayList<>();
        user = "volcom";
        lastUsage = "04-09-2022";
    }

    @Test
    void getAccounts() {
        listBankAccounts.add(bankAccountDTO);
        when(mongoTemplate.save(any())).thenReturn(bankAccountDTO);
        assertEquals(bankAccountService.getAccounts().size(), 5);
    }

    @Test
    void getAccountsActives(){
        listBankAccounts = bankAccountService.getAccountsActives();
        assertEquals(listBankAccounts.size(), 4);
    }

    @Test
    void accountsEncrypt(){
        listBankAccounts = bankAccountService.accountsEncrypt();
        assertEquals(listBankAccounts.size(), 5);
    }



    @Test
    void buildBankAccount(){
        when(bankAccountService.buildBankAccount(user,true,Country.MX,LocalDateTime.now())).thenReturn(bankAccountDTO);
        assertEquals(false,bankAccountDTO.isAccountActive());
    }

    @Test
    void deleteAccountsTest(){
        doNothing().when(bankingAccountRepository).deleteAll();
        bankAccountService.deleteAccounts();
    }


    @Test
    void getAccountByUser(){
        listBankAccounts = Arrays.asList(new BankAccountDTO());
        when(mongoTemplate.find(any(),any())).thenReturn(Collections.singletonList(listBankAccounts));
        bankAccountService.getAccountByUser(user);
        assertEquals(listBankAccounts.size(),1);

    }


    @Test
    void getAccountsFunctional(){
        listBankAccounts = Arrays.asList(new BankAccountDTO());
        when(bankAccountService.getAccountsFunctional()).thenReturn(listBankAccounts);
        assertEquals(listBankAccounts.size(),1);
    }


    @Test
    void getAccountDetails() {
        String date = "04-09-2022";
        BankAccountDTO bankAccountDTONew = bankAccountService.getAccountDetails("volcom", date);
        assertNotNull(bankAccountDTONew);
    }


    @Test
    void cadenaResponsabilidad(){

        bankAccountService.chainResponsa();


    }




}