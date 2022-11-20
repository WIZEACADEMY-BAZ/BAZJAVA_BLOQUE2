package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceImplTest {

  // Revisión: Uso de Mockito en cada prueba
  @Mock
  BankAccountDTO bankAccountDTOMock;
  @Mock
  MongoTemplate mongoTemplateMock;
  @Mock
  BankingAccountRepository bankingAccountRepositoryMock;
  @Mock
  List<BankAccountDTO> bankAccountDTOListMock;
  @Mock
  Stream<BankAccountDTO> bankAccountDTOStreamMock;
  @Mock
  Stream<String> bankAccountNameStreamMock;

  // Revisión: Prueba unitaria de cada endpoint de la API
  @InjectMocks
  BankAccountServiceImpl bankAccountService;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  //
  // getAccountsLocal
  //
  @Test
  void getAccountsLocalTest() {
    List<BankAccountDTO> bankAccountDTOList = bankAccountService.getAccountsLocal();
    assertAll(
        () -> assertNotNull(bankAccountDTOList)
        , () -> assertEquals(bankAccountDTOList.size(), 3));
  }

  //
  // getAccounts
  //
  @Test
  void getAccountsTest() {
    List<String> stringList = new ArrayList<>();
    stringList.add("Abraham");
    when((mongoTemplateMock.save(any(BankAccountDTO.class)))).thenReturn(bankAccountDTOMock);
    when(mongoTemplateMock.findAll(eq(BankAccountDTO.class))).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOListMock.stream()).thenReturn(bankAccountDTOStreamMock);
    when(bankAccountDTOStreamMock.map(bankAccountDTO -> bankAccountDTO.getUserName())).thenReturn(stringList.stream());

    assertNotNull(bankAccountService.getAccounts());
  }

  //
  // getAccountDetails
  //
  @Test
  void getAccountDetailsTest() {
    String userId = "1";
    String lastUsage = "12-03-2018";

    assertNotNull(bankAccountService.getAccountDetails(userId,lastUsage));
  }

  //
  // deleteAccounts
  //
  @Test
  void deleteAccountsTest() {
    doNothing().when(bankingAccountRepositoryMock).deleteAll();
    bankAccountService.deleteAccounts();
  }

  //
  // getAccountByUser
  //
  @Test
  void getAccountByUserTest() {
    String userId = "1";
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(mongoTemplateMock.find(any(Query.class),eq(BankAccountDTO.class))).thenReturn(bankAccountDTOListMock);

    assertNotNull(bankAccountService.getAccountByUser(userId));
  }

  //
  // changeCountry
  //
  // Revisión: Pruebas para Happy Path
  @Test
  void changeCountryTest() {
    long accountNumber = 1;
    String country = "MX";

    when(mongoTemplateMock.findOne(any(Query.class), eq(BankAccountDTO.class))).thenReturn(bankAccountDTOMock);
    when(bankingAccountRepositoryMock.save(bankAccountDTOMock)).thenReturn(bankAccountDTOMock);

    assertNotNull(bankAccountService.changeCountry(accountNumber,country));
  }

  // Revisión: Pruebas para cada Edge Case
  @Test
  void changeCountryNullTest() {
    long accountNumber = 1;
    String country = "MX";

    when(mongoTemplateMock.findOne(any(Query.class), eq(BankAccountDTO.class))).thenReturn(null);

    assertNotNull(bankAccountService.changeCountry(accountNumber,country));
  }

  @Test
  void createAccount() {

    when(bankingAccountRepositoryMock.save(bankAccountDTOMock)).thenReturn(bankAccountDTOMock);

    assertNotNull(bankAccountService.createAccount(bankAccountDTOMock));
  }
}