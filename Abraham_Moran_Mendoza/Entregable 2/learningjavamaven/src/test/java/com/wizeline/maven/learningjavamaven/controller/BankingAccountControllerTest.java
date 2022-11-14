package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankingAccountControllerTest {

  @Mock
  BankAccountService bankAccountServiceMock;
  @Mock
  AccountsJSONClient accountsJSONClientMock;
  @Mock
  BankAccountDTO bankAccountDTOMock;
  @Mock
  PostDTO postDTOMock;

  @InjectMocks
  private BankingAccountController bankingAccountController;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void sendUserAccount() {
    Integer userId = 1;
  }

  @Test
  void getAccountByUser() {
    String user = "user";
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);
    when(bankAccountServiceMock.getAccountByUser(user)).thenReturn(bankAccountDTOListMock);
    assertNotNull(bankingAccountController.getAccountByUser(user));
  }

  @Test
  void createAccount() {
    when(bankAccountServiceMock.createAccount(bankAccountDTOMock)).thenReturn(bankAccountDTOMock);
    assertNotNull(bankingAccountController.createAccount(bankAccountDTOMock));
  }

  @Test
  void testCreateAccountBBVA() {
    Integer trype = 1;
    assertNotNull(bankingAccountController.createAccount(trype,bankAccountDTOMock));
  }

  @Test
  void testCreateAccountAzteca() {
    Integer trype = 2;
    assertNotNull(bankingAccountController.createAccount(trype,bankAccountDTOMock));
  }

  @Test
  void testCreateAccountBanorte() {
    Integer trype = 3;
    assertNotNull(bankingAccountController.createAccount(trype,bankAccountDTOMock));
  }

  @Test
  void changeAccountCountry() {
    long accountNumber = 1;
    String country = "MX";

    Optional<BankAccountDTO> optionalMock = Optional.of(bankAccountDTOMock);
    when(bankAccountServiceMock.changeCountry(accountNumber,country)).thenReturn(optionalMock);

    assertNotNull(bankingAccountController.changeAccountCountry(accountNumber,country));
  }

  @Test
  void deleteAccounts() {
    doNothing().when(bankAccountServiceMock).deleteAccounts();
    ResponseEntity<String> responseEntityMock = bankingAccountController.deleteAccounts();
    assertEquals(responseEntityMock.getBody(),"All accounts deleted");
  }

  @Test
  void getExternalUser() {
    long userId = 1;

    when(accountsJSONClientMock.getPostById(userId)).thenReturn(postDTOMock);

    assertNotNull(bankingAccountController.getExternalUser(userId));
  }
}