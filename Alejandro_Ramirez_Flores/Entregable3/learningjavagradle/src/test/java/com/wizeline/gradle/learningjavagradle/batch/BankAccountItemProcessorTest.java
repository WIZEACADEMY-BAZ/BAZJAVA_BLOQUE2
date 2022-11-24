package com.wizeline.gradle.learningjavagradle.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

import com.mongodb.assertions.Assertions;
import com.wizeline.gradle.learningjavagradle.controller.UserController;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.wizeline.gradle.learningjavagradle.enums.AccountType.AHORRO;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountItemProcessorTest {
	
	private static final Logger LOGGER = Logger.getLogger(BankAccountItemProcessorTest.class.getName());
	
	@InjectMocks 
	BankAccountItemProcessor bankAccountItemProcessor;

	@Test
	void testProcess() {
		
		LOGGER.info("Preparando Prueba");
		BankAccountDTO account = new BankAccountDTO();
		account.setAccountNumber(3);
		account.setCountry("mexico");
		account.setAccountName("Cuenta alex");
		account.setAccountBalance(30.6);
		account.setUser("Alex");
		account.setAccountType(AHORRO);

        try {
        	LOGGER.info("Prueba Exitosa");
            bankAccountItemProcessor.process(account);
            Assertions.assertNotNull(account);
        } catch (Exception e) {
            e.printStackTrace();
    }
	}

}
