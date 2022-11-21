package com.wizeline.maven.learningjavagmaven.controller;

import com.wizeline.maven.learningjavagmaven.configuration.KafkaConfiguration;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import com.wizeline.maven.learningjavagmaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavagmaven.utils.CommonServices;
import jdk.jshell.execution.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.maven.learningjavagmaven.controller.BankingAccountController;


import javax.annotation.meta.When;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {



    @InjectMocks
    private BankingAccountController bankingController;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CommonServices commonServices;

    @Mock
    private  BankAccountModel bankAccountModel;

    @Mock
    private Util util;

    @Mock
    private KafkaConfiguration kafkaConfiguration;

    @Test
    public void getUserAccountTest() {
        String user ="Ana", pass="Pass1$";
        //ResponseEntity responseEntityTest= new ResponseEntity<>("All accounts deleted", HttpStatus.OK);

        ResponseModel response=new ResponseModel();
        response.setCode("OK000");

        lenient().when(commonServices.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountModel());
        ResponseEntity<?> responsefinal =bankingController.getUserAccount(user,pass,"18-11-2022");
        assertEquals(responsefinal.getStatusCode(),HttpStatus.OK);

    }

    @Test
    public void getAccountByUserTest(){
        String usuario  ="Luis";
        List<BankAccountModel> accounts = new ArrayList<>();

        when(bankAccountService.getAccountByUser(anyString())).thenReturn(accounts);

        ResponseEntity<List<BankAccountModel>> response=bankingController.getAccountByUser(usuario);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    void putAccount(  ) {
        //Ingreso de una cuenta
        //Resultado esperado
     //   String msjEsperado="Dato Ingresado" ;

      //  ResponseEntity respuesta1= bankingAccountController.putAccount("user2@wizeline.com","Pass1$", "01-10-2010");

    //    System.out.println(respuesta1.getClass());
      //  System.out.println(bankingAccountController.);
    //    assertEquals(msjEsperado,respuesta1);

    }

    @Test
    void deleteAccountsTest() {
        ResponseEntity response= new ResponseEntity("nada",HttpStatus.OK);
        List<BankAccountModel> accounts = new ArrayList<>();
        response = bankingController.deleteAccounts();
        assertEquals(response.getBody(),"All accounts deleted");
    }


    @Test
    void testPutAccount() {
    }

    @Test
    void getAccountsTest() {
    /*    List<BankAccountModel> responseEntity =new ArrayList<>();

        when(bankAccountService.getAccounts()).thenReturn(responseEntity);

        //ResponseEntity<List<BankingAccountController>> listResponseEntity= (ResponseEntity<List<BankingAccountController>>) bankAccountService.getAccounts();

        assertAll(
                () -> assertNotNull(responseEntity)
        //        () -> assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode()),
        //        () -> assertEquals(responseEntity,  listResponseEntity.getBody())
        );
   */ }

    @Test
    public void sendUserAccountTest(){
        BankAccountModel bankAccountModel =new BankAccountModel();
        List<BankAccountModel> accounts =new ArrayList<>();
        accounts.add(bankAccountModel);
        when(bankAccountService.getAccounts()).thenReturn(accounts);
    //    bankingController.sendUserAccount(0);
        assertEquals(1,accounts.size());

    }

    @Test
    void sendUserAccount() {
    }

    @Test
    void getUserAccount() {
    }

    @Test
    void getExternalUser() {
    }

    @Test
    void sayHelloGuest() {
    }


}