package com.wizeline.baz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.wizeline.baz.enums.BankAccountType;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.BankAccountDTO;
import com.wizeline.baz.model.request.CreateAccountRequest;
import com.wizeline.baz.model.response.BankAccountDetailsResponse;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateBankAccountResponseDTO;
import com.wizeline.baz.model.response.GetAccountsResponse;
import com.wizeline.baz.repository.BankAccountFactory;
import com.wizeline.baz.repository.BankAccountFactoryImp;
import com.wizeline.baz.repository.BankAccountRepository;
import com.wizeline.baz.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
	
	@Mock
	private BankAccountRepository bankAccountRepository;
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private BankAccountServiceImpl service;
	private BankAccountFactory bankAccountFactory = BankAccountFactoryImp.getInstance();
	
	@Test
	void createAccount_NoFoundUser() {
		String userId = "FAKE_USER_ID";
		CreateAccountRequest request = new CreateAccountRequest(userId, BankAccountType.PLATINUM);
		when(userRepository.existsById(anyString())).thenReturn(false);
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> service.createAccount(request));
		assertNotNull(exception);
		assertEquals(userId, exception.getUser());
	}
	
	@Test
	void createAccountPlatinum() {
		String userId = UUID.randomUUID().toString();
		CreateAccountRequest request = new CreateAccountRequest(userId, BankAccountType.PLATINUM);
		when(userRepository.existsById(anyString())).thenReturn(true);
		ResponseEntity<BaseResponseDTO> responseEntity = service.createAccount(request);	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(CreateBankAccountResponseDTO.class, responseEntity.getBody());
		CreateBankAccountResponseDTO response = (CreateBankAccountResponseDTO) responseEntity.getBody();
		assertCreateBankAccountResponse(response, BankAccountType.PLATINUM, 100.0);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "AHORRO", "NOMINA", "KID" } )
	void createAccountNotPlatinum(String accountTypeStr) {
		BankAccountType accountType = BankAccountType.valueOf(accountTypeStr);
		String userId = UUID.randomUUID().toString();
		CreateAccountRequest request = new CreateAccountRequest(userId, accountType);
		when(userRepository.existsById(anyString())).thenReturn(true);
		ResponseEntity<BaseResponseDTO> responseEntity = service.createAccount(request);	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(CreateBankAccountResponseDTO.class, responseEntity.getBody());
		CreateBankAccountResponseDTO response = (CreateBankAccountResponseDTO) responseEntity.getBody();
		assertCreateBankAccountResponse(response, accountType, 0);
	}
	
	@Test
	void getNotFoundAccountDetails() {
		when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.empty());
		ResponseEntity<BaseResponseDTO> responseEntity = service.getAccountDetails(System.currentTimeMillis());	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(BaseResponseDTO.class, responseEntity.getBody());
		BaseResponseDTO response = (BaseResponseDTO) responseEntity.getBody();
		assertNotNull(response.getErrors());
	}
	
	@Test
	void getAccountDetails() {
		String userId = UUID.randomUUID().toString();
		BankAccountDTO bankAccount = bankAccountFactory.createBankAccount(userId, BankAccountType.NOMINA);
		when(bankAccountRepository.findById(anyLong())).thenReturn(Optional.of(bankAccount));
		ResponseEntity<BaseResponseDTO> responseEntity = service.getAccountDetails(System.currentTimeMillis());	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(BankAccountDetailsResponse.class, responseEntity.getBody());
		BankAccountDetailsResponse response = (BankAccountDetailsResponse) responseEntity.getBody();
		assertEquals(bankAccount.getAccountNumber(), response.getAccountNumber());
		assertEquals(bankAccount.getAccountAlias(), response.getAccountAlias());
		assertEquals(bankAccount.getAccountType(), response.getAccountType());
	}
	
	@Test
	void getAccounts() {
		List<BankAccountDTO> bankAccounts = Arrays.asList(
				bankAccountFactory.createBankAccount("userId", BankAccountType.NOMINA),
				bankAccountFactory.createBankAccount("userId2", BankAccountType.AHORRO),
				bankAccountFactory.createBankAccount("userId3", BankAccountType.KID),
				bankAccountFactory.createBankAccount("userId4", BankAccountType.PLATINUM));
		when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
		ResponseEntity<BaseResponseDTO> responseEntity = service.getAccounts();	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(GetAccountsResponse.class, responseEntity.getBody());
		GetAccountsResponse response = (GetAccountsResponse) responseEntity.getBody();
		assertNotNull(response.getBankAccounts());
		assertEquals(bankAccounts.size(), response.getBankAccounts().size());
	}
	
	@Test
	void deleteAccount() {
		ResponseEntity<BaseResponseDTO> responseEntity = service.deleteAccount(System.currentTimeMillis());	
		assertNotNull(responseEntity);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertInstanceOf(BaseResponseDTO.class, responseEntity.getBody());
		BaseResponseDTO response = (BaseResponseDTO) responseEntity.getBody();
		assertNull(response.getErrors());
	}
	
	private void assertCreateBankAccountResponse(CreateBankAccountResponseDTO response, BankAccountType accountType, double balance) {
		assertTrue(response.getAccountNumber() > 0);
		assertEquals(balance, response.getAccountBalance());
		assertEquals(accountType, response.getAccountType());
		assertEquals(accountType.toString(), response.getAccountAlias());
	}
}
