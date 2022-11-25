package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankingAccountControllerTest {

    @Mock
    private BankAccountServiceImpl bankAccountService;

    @Mock
    private BankAccountDTO bankAccountDTO;

    @InjectMocks
    private BankingAccountController bankingAccountController;

    @BeforeEach
    void init(){
        System.out.println("@BeforeEach => iniciando");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAccountByUserTest(){
        System.out.println("@Test => getAccountByUserTest()");
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<BankAccountDTO>();
        bankAccountDTOList.add(bankAccountDTO);
        when(bankAccountService.getAccountByUser("user")).thenReturn(bankAccountDTOList);
        assertAll(
                () -> assertNotNull(bankingAccountController.getAccountByUser("user")),
                () -> assertEquals(bankingAccountController.getAccountByUser("user").getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void createAccountTest(){
        System.out.println("@Test => createAccountTest()");
        when(bankAccountService.createAccount(bankAccountDTO)).thenReturn(bankAccountDTO);
        assertAll(
                () -> assertNotNull(bankingAccountController.createAccount(bankAccountDTO)),
                () -> assertEquals(bankingAccountController.createAccount(bankAccountDTO).getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void changeAccountCountryTest(){
        System.out.println("@Test => changeAccountCountryTest()");
        when(bankAccountService.changeCountry((long) 1.0, "country")).thenReturn(Optional.of(bankAccountDTO));
        assertAll(
                () -> assertNotNull(bankingAccountController.changeAccountCountry((long) 1.0, "country")),
                () -> assertEquals(bankingAccountController.changeAccountCountry((long) 1.0, "country").getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void deleteAccountsTest(){
        System.out.println("@Test => deleteAccountsTest()");
        doNothing().when(bankAccountService).deleteAccounts();
        assertAll(
                () -> assertEquals(bankingAccountController.deleteAccounts().getBody(), "All accounts deleted"),
                () -> assertEquals(bankingAccountController.deleteAccounts().getStatusCode().value(), HttpStatus.OK.value())
        );
    }
}