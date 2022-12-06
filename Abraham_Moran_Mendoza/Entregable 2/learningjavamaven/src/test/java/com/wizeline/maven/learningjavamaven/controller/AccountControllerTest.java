package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountControllerTest {

  @Mock
  AccountServiceImpl accountService;

  @Mock
  BankAccountDTO bankAccountDTOMock;

  @InjectMocks
  AccountController accountController;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAccountTest() {
    String user = "user";
    String password = "password";
    String date = "date";

    when(accountService.getAccount(anyString(),anyString(),anyString())).thenReturn(bankAccountDTOMock);

    assertAll(
        () -> assertNotNull(accountController.getAccount(user,password,date))
        , () -> assertEquals(accountController.getAccount(user,password,date).getStatusCodeValue(), HttpStatus.OK.value())
    );
  }

  @Test
  void getAccountsTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(accountService.getAccounts()).thenReturn(bankAccountDTOListMock);

    assertAll(
        () -> assertNotNull(accountController.getAccounts())
        , () -> assertEquals(accountController.getAccounts().getStatusCodeValue(), HttpStatus.OK.value())
    );
  }

  @Test
  void getAccountByNameTest() {
    String name = "name";
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(accountService.getAccountByName(anyString())).thenReturn(bankAccountDTOListMock);

    assertAll(
        () -> assertNotNull(accountController.getAccountByName(name))
        , () -> assertEquals(accountController.getAccountByName(name).getStatusCodeValue(), HttpStatus.OK.value())
    );
  }

  @Test
  void getAccountByUserTest() {
    String user = "user";
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(accountService.getAccountByUser(anyString())).thenReturn(bankAccountDTOListMock);

    assertAll(
        () -> assertNotNull(accountController.getAccountByUser(user))
        , () -> assertEquals(accountController.getAccountByUser(user).getStatusCodeValue(), HttpStatus.OK.value())
    );
  }

  @Test
  void getAccountsGroupByTypeTest() {
    Map<String, List<BankAccountDTO>> bankAccountDTOMapMock = new HashMap<>();
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);
    bankAccountDTOMapMock.put("MockList",bankAccountDTOListMock);

    when(accountService.getAccountsGroupByType()).thenReturn(bankAccountDTOMapMock);

    assertAll(
        () -> assertNotNull(accountController.getAccountsGroupByType())
        , () -> assertEquals(accountController.getAccountsGroupByType().getStatusCodeValue(), HttpStatus.OK.value())
    );

  }

  @Test
  void getEncryptedAccountsTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(accountService.getEncryptedAccounts()).thenReturn(bankAccountDTOListMock);

    assertAll(
        () -> assertNotNull(accountController.getEncryptedAccounts())
        , () -> assertEquals(accountController.getEncryptedAccounts().getStatusCodeValue(), HttpStatus.OK.value())
    );
  }
}