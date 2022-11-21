package baz.practice.wizeline.learningjavamaven.controllertest;

import baz.practice.wizeline.learningjavamaven.client.AccountsJSONClient;
import baz.practice.wizeline.learningjavamaven.controller.BankingAccountController;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.model.Post;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.service.BankAccountBO;
import baz.practice.wizeline.learningjavamaven.service.UserBO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {

    @Mock
    UserBO userBOTest;
    @Mock
    BankAccountBO bankAccountServiceTest;
    @Mock
    AccountsJSONClient accountsJSONClient;
    @InjectMocks
    BankingAccountController bankingAccountController;
    @Mock
    BankAccountBO bankAccountService;
    @Mock
    private KafkaTemplate<Object, Object> template;

    @Test
    public void getAccountByUserTest(){
        String user = "Mario";
        List<BankAccountDTO> accounts = new ArrayList<>();

        lenient().when(bankAccountServiceTest.getAccountByUser(anyString())).thenReturn(accounts);

        ResponseEntity<List<BankAccountDTO>> response = bankingAccountController.getAccountByUser(user);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getUserAccountTest(){
        String user = "Ana", pass = "Pass1$";

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");


        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());

        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccount(user,pass,"18-11-2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getUserAccountTestInvalidPass(){
        String user = "Ana", pass = "pass", passInvalid="Password Incorrecto";

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");


        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());

        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccount(user,pass,"18-11-2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.FORBIDDEN);
        assertEquals(responsefinal.getBody(),passInvalid);
    }

    @Test
    public void getUserAccountTestInvalidDate(){
        String user = "Ana", pass = "Pas", responseText = "Formato de Fecha Incorrecto";

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");


        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());

        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccount(user,pass,"18/11/2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
        assertEquals(responsefinal.getBody(),responseText);
    }

    @Test
    public void  getUserAccountWUTest(){
        String user = "Lalo", pass = "Pass1$", date = "18-11-2022";
        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");

        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());
        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccountWU(user,pass,"18-11-2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getUserAccountWUTestInvalidPass(){
        String user = "Ana", pass = "p", passInvalid="Password Incorrecto";

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");


        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());

        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccountWU(user,pass,"18-11-2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
        assertEquals(responsefinal.getBody(),passInvalid);
    }

    @Test
    public void getUserAccountWUTestInvalidDate(){
        String user = "Ana", pass = "Pas", responseText = "Formato de Fecha Incorrecto";

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");


        lenient().when(userBOTest.login(anyString(),anyString())).thenReturn(response);
        lenient().when(bankAccountService.getAccountDetails(user,"18-11-2022")).thenReturn(new BankAccountDTO());

        ResponseEntity<?> responsefinal = bankingAccountController.getUserAccountWU(user,pass,"18/11/2022");
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
        assertEquals(responsefinal.getBody(),responseText);
    }

    @Test
    public void getAccountsTest(){
        List<BankAccountDTO> accounts = new ArrayList<>();

        lenient().when(bankAccountServiceTest.getAccounts()).thenReturn(accounts);

        ResponseEntity<List<BankAccountDTO>> response = bankingAccountController.getAccounts();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAccountsGroupByTypeTest() throws JsonProcessingException{
        List<BankAccountDTO> accounts = new ArrayList<>();

        lenient().when(bankAccountServiceTest.getAccounts()).thenReturn(accounts);

        ResponseEntity<Map<String, List<BankAccountDTO>>> responsefinal = bankingAccountController.getAccountsGroupByType();
        assertEquals(responsefinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void sayHelloGuestTest(){
        ResponseEntity<?> responseFinal = bankingAccountController.sayHelloGuest();
        assertEquals(responseFinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getAccountByNameTest(){
        String name = "Ana";
        BankAccountDTO response = new BankAccountDTO();

        Optional<String> optionalNombre = Optional.ofNullable(name);
        String nombreOp = optionalNombre.orElse("Bad Request");

        lenient().when(bankAccountServiceTest.getAccountByName(nombreOp)).thenReturn(response);

        ResponseEntity<?> responseFinal = bankingAccountController.getAccountByName(name);
        assertEquals(responseFinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getEncryptedAccountsTest(){
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        lenient().when(bankAccountService.getAccounts()).thenReturn(accounts);

        ResponseEntity<?> responseFinal = bankingAccountController.getEncryptedAccounts();
        assertEquals(responseFinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void getEncryptedAccountsTestInvalidKey() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };

        byte[] ivBytes = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
        };
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AUS");
        Cipher cipher = null;
        cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

        NoSuchProviderException errorProvi = assertThrows(NoSuchProviderException.class, () -> {
           bankingAccountController.getEncryptedAccounts();
        });

        System.out.println(errorProvi);
    }

    @Test
    public void getExternalUserTest(){
        Long userId = 23123123L;

        lenient().when(accountsJSONClient.getPostById(userId)).thenReturn(new Post());

        ResponseEntity<?> responseFinal = bankingAccountController.getExternalUser(userId);
        assertEquals(responseFinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void sendUserAccount(){
        int userIdTest = 13;
        List<BankAccountDTO> accountsTest = new ArrayList<>();

        accountsTest.add(new BankAccountDTO());

        lenient().when(bankAccountServiceTest.getAccounts()).thenReturn(accountsTest);

        bankingAccountController.sendUserAccount(userIdTest);
        assertTrue(accountsTest.get(0) != null);

    }

    @Test
    public void updateAccountTest(){
        String accountNameStringTest = "ana_banana", newAccountNameTest = "Ana_sponja";
        BankAccountDTO resCuenta = new BankAccountDTO();

        lenient().when(bankAccountService.updateAccount(accountNameStringTest,newAccountNameTest)).thenReturn(resCuenta);

        ResponseEntity<?> responseFinal = bankingAccountController.updateAccount(accountNameStringTest,newAccountNameTest);
        assertEquals(responseFinal.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void apiExternalTest(){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<?> responseFinal = bankingAccountController.apiExternal();
        assertEquals(responseFinal.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deletedAccountsTest(){

        ResponseEntity<?> responseFinal = bankingAccountController.deleteAccounts();
        assertEquals(responseFinal.getBody(),"All accounts deleted");
    }
}
