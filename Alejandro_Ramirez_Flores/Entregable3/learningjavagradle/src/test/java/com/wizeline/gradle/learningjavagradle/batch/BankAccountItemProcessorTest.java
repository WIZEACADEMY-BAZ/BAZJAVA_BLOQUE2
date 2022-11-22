package com.wizeline.gradle.learningjavagradle.batch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.logging.Logger;

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
	
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@InjectMocks 
	BankAccountItemProcessor bankAccountItemProcessor;

	@Test
	void testProcess() {
		
		LOGGER.info("Preparando Prueba");
		BankAccountDTO o = new BankAccountDTO();
        o.setAccountNumber(3);
        o.setCountry("mexico");
        o.setAccountName("mex");
        o.setAccountBalance(3);
        o.setUser("mx");
        o.setAccountType(AHORRO);

        try {
        	LOGGER.info("Prueba Exitosa");
            bankAccountItemProcessor.process(o);
        } catch (Exception e) {
            e.printStackTrace();
    }
	}

}
