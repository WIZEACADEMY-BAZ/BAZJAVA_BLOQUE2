package com.Wizeline.maven.learningjavamaven.batch;

import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


public class BankAccountItemProcessor implements ItemProcessor<BankAccountDTO, String> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BankAccountItemProcessor.class);

    @Override
    public String process(BankAccountDTO bankAccountDTO) throws Exception {
        String accountType = bankAccountDTO.getAccountType().toString();
        String accounts = "Country: " + bankAccountDTO.getCountry() + "Account Name: " + bankAccountDTO.getAccountName() + "Account Type: " + accountType + "Account Balance: " + bankAccountDTO.getAccountBalance() + "User: " + bankAccountDTO.getUserName();
        LOGGER.info("converting '{ }' into '{ }'", bankAccountDTO, accounts);
        return accounts;
    }

}
