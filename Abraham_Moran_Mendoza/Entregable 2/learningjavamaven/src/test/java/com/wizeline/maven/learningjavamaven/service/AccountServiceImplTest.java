package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.utils.exceptions.ExcepcionGenerica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

class AccountServiceImplTest {

  @Mock
  private List<BankAccountDTO> bankAccountDTOListMock;

  @Mock
  private BankAccountDTO bankAccountDTOMock;

  @Mock
  private UserService userServiceMock;

  @Mock
  private BankAccountService bankAccountServiceMock;

  @InjectMocks
  AccountServiceImpl accountService;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAccountTest() {
    String user = "user";
    String password = "Pass1@";
    String lastUsage = "10-11-2022";

    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setCode(SUCCESS_CODE);

    when(bankAccountDTOMock.getUserName()).thenReturn(user);
    when(userServiceMock.getUserMongo(anyString(),anyString())).thenReturn(responseDTO);
    when(bankAccountServiceMock.getAccountDetails(anyString(),anyString())).thenReturn(bankAccountDTOMock);

    assertAll(
        () -> assertNotNull(accountService.getAccount(user,password,lastUsage))
        , () -> assertEquals(accountService.getAccount(user,password,lastUsage).getUserName(), user)
    );
  }

  @Test
  void getAccountNotFoundTest() {
    String user = "user";
    String password = "Pass1@";
    String lastUsage = "10-11-2022";

    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setCode(HttpStatus.NOT_FOUND.toString());

    when(userServiceMock.getUserMongo(anyString(),anyString())).thenReturn(responseDTO);

    assertThrows(ExcepcionGenerica.class, () ->{
      accountService.getAccount(user,password,lastUsage);
    });
  }

  @Test
  void getAccountPasswordFormatInvalidTest() {
    String user = "user";
    String password = "password";
    String lastUsage = "10-11-2022";

    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setCode(HttpStatus.NOT_FOUND.toString());

    when(userServiceMock.getUserMongo(anyString(),anyString())).thenReturn(responseDTO);

    assertThrows(ResponseStatusException.class, () ->{
      accountService.getAccount(user,password,lastUsage);
    });
  }

  @Test
  void getAccountFormatInvalidTest() {
    String user = "user";
    String password = "Pass1@";
    String lastUsage = "date";

    assertThrows(ResponseStatusException.class, () ->{
      accountService.getAccount(user,password,lastUsage);
    });
  }

  @Test
  void getAccountsTest() {
    List<BankAccountDTO> accountsMock = new ArrayList<>();
    accountsMock.add(bankAccountDTOMock);
    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(accountsMock);
    assertNotNull(accountService.getAccounts());
  }

  @Test
  void getAccountByNameTest() {
    String name = "name";

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);

    doNothing().when(bankAccountDTOListMock).clear();
    when(bankAccountDTOListMock.size()).thenReturn(2);
    when(bankAccountDTOListMock.get(anyInt())).thenReturn(bankAccountDTOMock);

    when(bankAccountDTOMock.getAccountName()).thenReturn(name);

    assertAll(
        () -> assertNotNull(accountService.getAccountByName(name))
        , () -> assertEquals(accountService.getAccountByName(name).size(), 2)
    );
  }

  @Test
  void getAccountByNameEmptyTest() {
    String name = "name";

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);

    doNothing().when(bankAccountDTOListMock).clear();
    when(bankAccountDTOListMock.size()).thenReturn(2);
    when(bankAccountDTOListMock.get(anyInt())).thenReturn(bankAccountDTOMock);

    when(bankAccountDTOMock.getAccountName()).thenReturn("pedro");

    assertAll(
        () -> assertNotNull(accountService.getAccountByName(name))
        , () -> assertEquals(accountService.getAccountByName(name).size(), 2)
    );
  }

  @Test
  void getAccountByUserTest() {
    String userString = "user";

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);

    doNothing().when(bankAccountDTOListMock).clear();
    when(bankAccountDTOListMock.size()).thenReturn(2);
    when(bankAccountDTOListMock.get(anyInt())).thenReturn(bankAccountDTOMock);

    when(bankAccountDTOMock.getUserName()).thenReturn(userString);

    assertAll(
        () -> assertNotNull(accountService.getAccountByUser(userString))
        , () -> assertEquals(accountService.getAccountByUser(userString).size(), 2)
    );
  }

  @Test
  void getAccountByUserEmptyTest() {
    String userString = "user";

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);

    doNothing().when(bankAccountDTOListMock).clear();
    when(bankAccountDTOListMock.size()).thenReturn(2);
    when(bankAccountDTOListMock.get(anyInt())).thenReturn(bankAccountDTOMock);

    when(bankAccountDTOMock.getUserName()).thenReturn("pedro");

    assertAll(
        () -> assertNotNull(accountService.getAccountByUser(userString))
        , () -> assertEquals(accountService.getAccountByUser(userString).size(), 2)
    );
  }

  @Test
  void getAccountsGroupByType() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);
    AccountType accountType = AccountType.NOMINA;
    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountType()).thenReturn(accountType);

    assertNotNull(accountService.getAccountsGroupByType());
  }

  @Test
  void getEncryptedAccounts() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    assertNotNull(accountService.getEncryptedAccounts());
  }

  @Test
  void getEncryptedAccountsNoSuchAlgorithmExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          // further stubbings ...
          doNothing().when(mock).init(anyInt(),any(SecretKeySpec.class), any(IvParameterSpec.class));
          when(mock.getOutputSize(anyInt())).thenThrow(NoSuchAlgorithmException.class);
        })) {

      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });
    }
  }

  @Test
  void getEncryptedAccountsNoSuchPaddingExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doNothing().when(mock).init(anyInt(),any(SecretKeySpec.class), any(IvParameterSpec.class));
          when(mock.getOutputSize(anyInt())).thenThrow(NoSuchProviderException.class);
        })) {

      try (MockedStatic<Cipher> mockedStatic = Mockito.mockStatic(Cipher.class)) {

        mockedStatic
            .when(() -> Cipher.getInstance(anyString(),anyString())).thenThrow(NoSuchPaddingException.class);

        assertThrows(RuntimeException.class, () ->{
          accountService.getEncryptedAccounts();
        });

      }
    }
  }

  @Test
  void getEncryptedAccountsNoSuchProviderExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doNothing().when(mock).init(anyInt(),any(SecretKeySpec.class), any(IvParameterSpec.class));
          when(mock.getOutputSize(anyInt())).thenThrow(NoSuchProviderException.class);
        })) {

      try (MockedStatic<Cipher> mockedStatic = Mockito.mockStatic(Cipher.class)) {

        mockedStatic
            .when(() -> Cipher.getInstance(anyString(),anyString())).thenThrow(NoSuchProviderException.class);

        assertThrows(RuntimeException.class, () ->{
          accountService.getEncryptedAccounts();
        });

      }
    }
  }

  @Test
  void getEncryptedAccountsInvalidAlgorithmParameterExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doThrow(InvalidAlgorithmParameterException.class).when(mock).init(anyInt(),any(SecretKeySpec.class), any(IvParameterSpec.class));
        })) {

      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });
    }
  }

  @Test
  void getEncryptedAccountsShortBufferExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doThrow(ShortBufferException.class).when(mock).update(any(byte[].class),anyInt(),anyInt(),any(byte[].class),anyInt());
        })) {

      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });
    }
  }

  @Test
  void getEncryptedAccountsIllegalBlockSizeExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doNothing().when(mock).update(any(byte[].class),anyInt(),anyInt(),any(byte[].class),anyInt());
          doThrow(IllegalBlockSizeException.class).when(mock).doFinal(any(byte[].class),anyInt());
        })) {

      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });
    }
  }

  @Test
  void getEncryptedAccountsBadPaddingExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doThrow(BadPaddingException.class).when(mock).doFinal(any(byte[].class),anyInt());
        })) {

      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });
    }
  }

  @Test
  void getEncryptedAccountsInvalidKeyExceptionTest() {
    List<BankAccountDTO> bankAccountDTOListMock = new ArrayList<>();
    bankAccountDTOListMock.add(bankAccountDTOMock);

    when(bankAccountServiceMock.getAccountsLocal()).thenReturn(bankAccountDTOListMock);
    when(bankAccountDTOMock.getAccountName()).thenReturn("AccountName");
    when(bankAccountDTOMock.getCountry()).thenReturn("MX");

    try (MockedConstruction<Cipher> mocked = Mockito.mockConstruction(Cipher.class,
        (mock, context) -> {
          doThrow(InvalidKeyException.class).when(mock).init(anyInt(),any(SecretKeySpec.class), any(IvParameterSpec.class));
        })) {
      assertThrows(RuntimeException.class, () ->{
        accountService.getEncryptedAccounts();
      });

    }
  }
}