package com.wizeline.maven.learninjavamaven.config;

import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Processor for BankAccountJob.
 */
public class BankAccountItemProcessor implements ItemProcessor<BankAccountDTO, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountItemProcessor.class);

    @Override
    public String process(BankAccountDTO bankAccountDTO) throws Exception {
        String accountType = bankAccountDTO.getAccountType().toString();
        String accounts = "Country: " + bankAccountDTO.getCountry() + "Account Name: " + bankAccountDTO.getAccountName()
                + "Account Type: " + accountType + "Account Balance: " + bankAccountDTO.getAccountBalance()
                + "User: " + bankAccountDTO.getAccountName();
        LOGGER.info("converting '{}' into '{}'", bankAccountDTO, accounts);
        return accounts;
    }
}
