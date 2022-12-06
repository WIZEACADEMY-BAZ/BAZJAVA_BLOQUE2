package com.wizeline.gradle.learningjavagradle.batch;

import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

@WebMvcTest(BankAccountItemProcessor.class)
class BankAccountItemProcessorTest {
	private static final Logger LOGGER = Logger.getLogger(BankAccountItemProcessor.class.getName());
	@Test
	void BankAccountItemProccesor() throws Exception {
		LOGGER.info("processor batch");
		BankAccountDTO account = new BankAccountDTO();
		account.setCountry("MEXICO");
		account.setAccountName("cuenta de david 1");
		account.setAccountType(AccountType.NOMINA);
		account.setAccountBalance(218.5);
		account.setUser("David");
		BankAccountItemProcessor processor = new BankAccountItemProcessor();
		String resp =processor.process(account);
		LOGGER.info("respuesta "+resp);
		Assertions.assertNotNull(resp);
	}

}
