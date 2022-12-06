package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.wizeline.gradle.learningjavagradle.enums.AccountType.AHORRO;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountDTOTest {
	
	@InjectMocks
    private BankAccountDTO bankAccountDTO;

	@Test
	void testGetAccountNumber() {
		bankAccountDTO.getAccountNumber();
	}

	@Test
	void testSetAccountNumber() {
		bankAccountDTO.setAccountNumber(1);
	}

	@Test
	void testGetAccountName() {
		 bankAccountDTO.getAccountName();
	}

	@Test
	void testSetAccountName() {
		bankAccountDTO.setAccountName("Ahorro");
	}

	@Test
	void testGetUser() {
		bankAccountDTO.getUser();
	}

	@Test
	void testSetUser() {
		bankAccountDTO.setUser("Alex");

	}

	@Test
	void testGetAccountBalance() {
		bankAccountDTO.getAccountBalance();
	}

	@Test
	void testSetAccountBalance() {
		bankAccountDTO.setAccountBalance(200);
	}

	@Test
	void testGetAccountType() {
		bankAccountDTO.getAccountType();
	}

	@Test
	void testSetAccountType() {
		bankAccountDTO.setAccountType(AHORRO);
	}

	@Test
	void testGetCountry() {
		bankAccountDTO.getCountry();
	}

	@Test
	void testSetCountry() {
		bankAccountDTO.setCountry("Mexico");
	}

	@Test
	void testIsAccountActive() {
		bankAccountDTO.isAccountActive();
	}

	@Test
	void testSetAccountActive() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCreationDate() {
		bankAccountDTO.setAccountActive(true);
	}

	@Test
	void testSetCreationDate() {
		//bankAccountDTO.setCreationDate();
	}

	@Test
	void testGetLastUsage() {
		bankAccountDTO.getLastUsage();
	}

	@Test
	void testSetLastUsage() {
		//bankAccountDTO.setLastUsage(1);
	}

}
