package com.wizeline.learningjavamaven.controller;

import com.wizeline.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.learningjavamaven.enums.AccountType;
import com.wizeline.learningjavamaven.model.BankAccountDTO;
import com.wizeline.learningjavamaven.model.Post;
import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.service.BankAccountService;
import com.wizeline.learningjavamaven.utils.CommonServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankingAccountControllerTest {

  @InjectMocks
  BankingAccountController bankingAccountController;

  @Mock
  CommonServices commonServices;

  @Mock
  BankAccountService bankAccountService;

  @Mock
  AccountsJSONClient accountsJSONClient;

  @Mock
  KafkaTemplate<Object, Object> template;

  ResponseDTO responseDTO = new ResponseDTO();
  BankAccountDTO bankAccountDTO = new BankAccountDTO();

  @BeforeEach
  void before() {
    bankAccountDTO.setUserName("username");
    bankAccountDTO.setAccountNumber(12L);
    bankAccountDTO.setAccountType(AccountType.PLATINUM);
    responseDTO.setStatus("correcto");
  }

  @Test
  @DisplayName("Cuenta de usuario controlador")
  void getUserAccount() {
    responseDTO.setCode("OK000");
    when(commonServices.login(Mockito.any(), Mockito.anyString())).thenReturn(responseDTO);
    when(bankAccountService.getAccountDetails(Mockito.any(), Mockito.anyString())).thenReturn(bankAccountDTO);
    ResponseEntity<?> response = bankingAccountController.getUserAccount("alex", "Thenew4@", "01-09-1989");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Cuenta de usuario codigo error")
  void getUserAccountCode() {
    responseDTO.setCode("OK001");
    when(commonServices.login(Mockito.any(), Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<?> response = bankingAccountController.getUserAccount("alex", "Thenew4@", "01-09-1989");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Cuenta de usuario password invalido")
  void getUserAccountPassInvalid() {
    responseDTO.setCode("OK001");
    ResponseEntity<?> response = bankingAccountController.getUserAccount("alex", "123", "01-09-1989");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Cuenta de usuario fecha invalida")
  void getUserAccountDateInvalid() {
    responseDTO.setCode("OK001");
    ResponseEntity<?> response = bankingAccountController.getUserAccount("alex", "123", "41-09/1989");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Lista de cuentas de usuarios")
  void getAccounts() {
    when(bankAccountService.getAccounts()).thenReturn(Collections.singletonList(bankAccountDTO));
    ResponseEntity<List<BankAccountDTO>> response = bankingAccountController.getAccounts();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Lista de cuentas de usuarios encriptada")
  void getEncryptedAccounts() {
    when(bankAccountService.getEncryptedAccount()).thenReturn(Collections.singletonList(bankAccountDTO));
    ResponseEntity<List<BankAccountDTO>> response = bankingAccountController.getEncryptedAccounts();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Cuenta de usuario por usuario")
  void getAccountByUser() {
    when(bankAccountService.getAccountByUser(Mockito.any())).thenReturn(Collections.singletonList(bankAccountDTO));
    ResponseEntity<List<BankAccountDTO>> response = bankingAccountController.getAccountByUser("alex");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Lista de cuentas de usuarios por grupo")
  void getAccountsGroupByType() {
    when(bankAccountService.getAccounts()).thenReturn(Collections.singletonList(bankAccountDTO));
    ResponseEntity<Map<String, List<BankAccountDTO>>> response = bankingAccountController.getAccountsGroupByType();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Muestra say hello")
  void sayHelloGuest() {
    ResponseEntity<String> response = bankingAccountController.sayHelloGuest();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Eliminacion de cuentas")
  void deleteAccounts() {
    doNothing().when(bankAccountService).deleteAccounts();
    ResponseEntity<String> response = bankingAccountController.deleteAccounts();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Usuario externo")
  void getExternalUser() {
    Post post = new Post();
    post.setUserId("alex");
    post.setBody("body");
    post.setTitle("titulo");
    when(accountsJSONClient.getPostById(Mockito.any())).thenReturn(post);
    ResponseEntity<Post> response = bankingAccountController.getExternalUser(1L);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Envio usuario cuenta")
  void sendUserAccount() {
    when(bankAccountService.getAccounts()).thenReturn(Collections.singletonList(bankAccountDTO));
    when(template.send(Mockito.anyString(), Mockito.any())).thenReturn(null);
    bankingAccountController.sendUserAccount(0);
  }

  @Test
  @DisplayName("Envio usuario cuenta nulo")
  void sendUserAccountNull() {
    when(bankAccountService.getAccounts()).thenReturn(null);
    bankingAccountController.sendUserAccount(0);
  }

  @Test
  @DisplayName("Envio usuario cuenta vacia")
  void sendUserAccountEmpty() {
    when(bankAccountService.getAccounts()).thenReturn(new ArrayList<>());
    bankingAccountController.sendUserAccount(0);
  }
}