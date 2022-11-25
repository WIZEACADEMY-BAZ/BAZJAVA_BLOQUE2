package com.wizeline.gradle.practicajava.service;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wizeline.gradle.practicajava.enums.AccountType;
import com.wizeline.gradle.practicajava.model.BankAccountDTO;
import com.wizeline.gradle.practicajava.repository.BankingAccountRepository;

@ContextConfiguration(classes = { BankAccountServiceImpl.class })
@ExtendWith(SpringExtension.class)
class BankAccountServiceImplTest {

	@MockBean
	private MongoTemplate mongoTemplate;

	@Autowired
	private BankAccountServiceImpl bankAccountServiceImpl;

	@MockBean
	private BankingAccountRepository bankingAccountRepository;

	/**
	 * Metodo Test: {@link BankAccountServiceImpl#getAccounts()}
	 */
	@Test
	void getAccountsTest() {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();

		bankAccountDTO.setAccountActive(true);
		bankAccountDTO.setAccountBalance(0);
		bankAccountDTO.setAccountName("Cuenta");
		bankAccountDTO.setAccountNumber(000001);
		bankAccountDTO.setAccountType(AccountType.AHORRO);
		bankAccountDTO.setCountry("Mexico");
		bankAccountDTO.setCreationDate(LocalDateTime.now());
		bankAccountDTO.setLastUsage(LocalDateTime.now());
		bankAccountDTO.setUser("Usuario");

		when(mongoTemplate.save((BankAccountDTO) any())).thenReturn(bankAccountDTO);
		when(mongoTemplate.findAll((Class<BankAccountDTO>) any())).thenReturn(new ArrayList<>());
		assertEquals(3, bankAccountServiceImpl.getAccounts().size());
		verify(mongoTemplate, atLeast(1)).save((BankAccountDTO) any());
		verify(mongoTemplate).findAll((Class<BankAccountDTO>) any());
	}

	/**
	 * Metodo Test: {@link BankAccountServiceImpl#getAccountDetails()}
	 */
	@Test
	void getAccountDetailsTest() {
		String user = "Usuario";
		String fecha = "01-01-2022";

		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate usage = LocalDate.parse(fecha, dateformatter);

		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountActive(true);
		bankAccountDTO.setAccountBalance(0);
		bankAccountDTO.setAccountName("Cuenta");
		bankAccountDTO.setAccountNumber(000001);
		bankAccountDTO.setAccountType(AccountType.AHORRO);
		bankAccountDTO.setCountry("MX");
		bankAccountDTO.setCreationDate(LocalDateTime.now());
		bankAccountDTO.setLastUsage(usage.atStartOfDay());
		bankAccountDTO.setUser("Usuario");

		assertEquals(bankAccountDTO.getUser(), bankAccountServiceImpl.getAccountDetails(user, fecha).getUser());

	}

	/**
	 * Metodo Test: {@link BankAccountServiceImpl#deleteAccounts()}
	 */
	@Test
	void deleteAccountsTest() {
		doNothing().when(bankingAccountRepository).deleteAll();
		bankAccountServiceImpl.deleteAccounts();
		verify(bankingAccountRepository).deleteAll();
	}

	/**
	 * Metodo Test: {@link BankAccountServiceImpl#getAccountsByUser(String)}
	 */
	@Test
	void getAccountByUserTest() {
		List<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
		when(mongoTemplate.find((Query) any(), (Class<BankAccountDTO>) any())).thenReturn(bankAccountDTOList);
		List<BankAccountDTO> actualAccountByUser = bankAccountServiceImpl.getAccountsByUser("User");
		assertSame(bankAccountDTOList, actualAccountByUser);
		assertTrue(actualAccountByUser.isEmpty());
		verify(mongoTemplate).find((Query) any(), (Class<BankAccountDTO>) any());
	}

}
