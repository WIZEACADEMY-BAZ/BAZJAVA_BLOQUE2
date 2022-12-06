package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.enums.AccountType;
import com.wizeline.learningjavamaven.model.BankAccountDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

  @InjectMocks
  BankAccountServiceImpl bankAccountService;

  @Mock
  UserService userService;

  @Mock
  MongoTemplate mongoTemplate;

  @Mock
  BankingAccountRepository bankAccountRepository;

  BankAccountDTO bankAccountDTO = new BankAccountDTO();
  UserDTO userDTO = new UserDTO();

  @BeforeEach
  void before() {
    userDTO.setId("id");
    userDTO.setUsername("alex");
    userDTO.setPassword("123");
    bankAccountDTO.setUserName("username");
    bankAccountDTO.setAccountNumber(12L);
    bankAccountDTO.setAccountType(AccountType.PLATINUM);
  }

  @Test
  void getAccountsNull() {
    List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
    newBankAccountDTOS.add(bankAccountDTO);
    when(userService.getUser(Mockito.anyString())).thenReturn(null);
    when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(newBankAccountDTOS);
    List<BankAccountDTO> bankAccountDTOS = bankAccountService.getAccounts();
    assertTrue(bankAccountDTOS.size() == 0);
  }

  @Test
  void getAccountsBankNull() {
    List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
    newBankAccountDTOS.add(bankAccountDTO);
    when(userService.getUser(Mockito.anyString())).thenReturn(userDTO);
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
    List<BankAccountDTO> bankAccountDTOS = bankAccountService.getAccounts();
    assertTrue(bankAccountDTOS.size() > 0);
  }

  @Test
  void deleteAccounts() {
    doNothing().when(bankAccountRepository).deleteAll();
    bankAccountService.deleteAccounts();
  }

  @Test
  void getAccountByUser() {
    when(mongoTemplate.find(Mockito.any(), Mockito.any())).thenReturn(Collections.singletonList(bankAccountDTO));
    List<BankAccountDTO> bankAccountDTOS = bankAccountService.getAccountByUser("alex");
    assertTrue(bankAccountDTOS.size() > 0);
  }

  @Test
  void getAccountDetails() {
    String date = "01-09-1989";
    BankAccountDTO bankAccountDTONew = bankAccountService.getAccountDetails("alex", date);
    assertNotNull(bankAccountDTONew);
  }

  @Test
  void getEncryptedAccount() {
    List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
    newBankAccountDTOS.add(bankAccountDTO);
    when(userService.getUser(Mockito.anyString())).thenReturn(userDTO);
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(bankAccountDTO);
    when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(newBankAccountDTOS);
    List<BankAccountDTO> bankAccountDTOS = bankAccountService.getEncryptedAccount();
    assertTrue(bankAccountDTOS.size() > 0);
  }
}