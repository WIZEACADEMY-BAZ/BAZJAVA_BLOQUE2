package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.client.AccountsJSONClient;
import com.baz.wizeline.learningspring.model.BankAccountDTO;
import com.baz.wizeline.learningspring.model.Post;
import com.baz.wizeline.learningspring.model.ResponseDTO;
import com.baz.wizeline.learningspring.service.BankAccountService;
import com.baz.wizeline.learningspring.utils.CommonServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankingAccountControllerTest {

    @Mock
    BankAccountService bankAccountService;

    @Mock
    CommonServices commonServices;

    @Mock
    AccountsJSONClient accountsJSONClient;

    @Mock
    KafkaTemplate<Object, Object> template;

    @InjectMocks
    BankingAccountController controller;


    ResponseDTO responseDTO;
    BankAccountDTO bankAccountDTO;
    Post post;
    List<BankAccountDTO> listCuentas;


    @BeforeEach
    void setUp() {
        responseDTO = new ResponseDTO();
        bankAccountDTO = new BankAccountDTO();
        post = new Post();
        listCuentas = new ArrayList<>();

        //Se agregan parametros al post
        post.setUserId("Volcom");
        post.setBody("Getting post body...");
        post.setTitle("Getting post title...");
        post.setId(1L);


    }

    @Test
    @DisplayName("Creacion de cuenta exitosa")
    void getUserAccount() {
        responseDTO.setCode("OK000");
        when(commonServices.login(any(), anyString())).thenReturn(responseDTO);
        when(bankAccountService.getAccountDetails(any(), anyString())).thenReturn(bankAccountDTO);
        ResponseEntity<?> response = controller.getUserAccount("volcom", "Akali89@", "04-09-1992");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Creacion de cuenta con errores")
    void getUserAccountWithErrors() {
        responseDTO.setCode("ER001");
        responseDTO.setStatus("fail");
        when(commonServices.login(any(), anyString())).thenReturn(responseDTO);
        ResponseEntity<?> error = controller.getUserAccount("error", "Akali89@", "04-09-1992");
        assertNotNull(error);
        ResponseEntity<?> errorPass = controller.getUserAccount("error", "123", "04-09-1992");
        assertNotNull(errorPass);
        ResponseEntity<?> errorDate = controller.getUserAccount("error", "Akali89@", "04/202209");
        assertNotNull(errorDate);

    }

    @Test
    @DisplayName("Obtener lista de cuentas encryptadas")
    void getAccountsEncrypt() {
        when(bankAccountService.accountsEncrypt()).thenReturn(listCuentas);
        ResponseEntity<List<BankAccountDTO>> response = controller.getEncryptedAccounts();
        assertNotNull(response);
    }


    @Test
    @DisplayName("Obtener lista de cuentas")
    void getAccounts() {
        when(bankAccountService.getAccounts()).thenReturn(listCuentas);
        ResponseEntity<List<BankAccountDTO>> response = controller.getAccounts();
        assertNotNull(response);
    }


    @Test
    @DisplayName("Obtener cuentas Functional")
    void getAccountsFunctional(){
        when(bankAccountService.getAccountsFunctional()).thenReturn(listCuentas);
        ResponseEntity<List<BankAccountDTO>> response = controller.getAccountsFunctional();
        assertNotNull(response);

    }

    @Test
    @DisplayName("Obtener cuentas Activas")
    void getAccountsActives(){
        when(bankAccountService.getAccountsActives()).thenReturn(listCuentas);
        ResponseEntity<List<BankAccountDTO>> response = controller.getAccountsActives();
        assertNotNull(response);

    }

    @Test
    @DisplayName("Borrar cuentas")
    void deleteAccounts(){
        doNothing().when(bankAccountService).deleteAccounts();
        ResponseEntity<String> response = controller.deleteAccounts();
        assertNotNull(response);

    }

    @Test
    @DisplayName("Lista de cuentas por Usuario")
    void getAccountByUser() {
        when(bankAccountService.getAccountByUser(any())).thenReturn(listCuentas);
        ResponseEntity<List<BankAccountDTO>> response = controller.getAccountByUser("alex");
        assertNotNull(response);
    }

    @Test
    @DisplayName("Lista de cuentas de usuarios por grupo")
    void getAccountsGroupByType() throws JsonProcessingException {
        when(bankAccountService.getAccounts()).thenReturn(listCuentas);
        ResponseEntity<Map<String, List<BankAccountDTO>>> response = controller.getAccountsGroupByType();
        assertNotNull(response);
    }

    @Test
    @DisplayName("Ejemplo HelloGuest")
    void sayHelloGuest() {
        ResponseEntity<String> response = controller.sayHelloGuest();
        assertNotNull(response);
    }

    @Test
    @DisplayName("External User")
    void getExternalUser() {
        when(accountsJSONClient.getPostById(any())).thenReturn(post);
        ResponseEntity<Post> response = controller.getExternalUser(1L);
        assertNotNull(response);
    }


    @Test
    @DisplayName("Template Kafka")
    void sendUserAccount() {
        when(bankAccountService.getAccounts()).thenReturn(Collections.singletonList(bankAccountDTO));
        when(template.send(anyString(), any())).thenReturn(null);
        controller.checkActivesAccount(0);
    }

    @Test
    @DisplayName("Cadena de Responsabilidad")
    void checkResponsability(){
        doNothing().when(bankAccountService).chainResponsa();
        controller.checkResponsability();



    }



}