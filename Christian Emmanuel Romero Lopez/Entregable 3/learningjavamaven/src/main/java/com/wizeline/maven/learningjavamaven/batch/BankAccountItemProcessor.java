
package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Processor for BankAccountJob.
 */
public class BankAccountItemProcessor implements ItemProcessor<BankAccountModel, String> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BankAccountItemProcessor.class);

    @Override
    public String process(BankAccountModel bankAccountModel) throws Exception {
        String accountType = bankAccountModel.getAccountType().toString();
        String accounts = "Country: " + bankAccountModel.getCountry() + " Account Name: " + bankAccountModel.getAccountName()
                + " Account Type: " + accountType + " Account Balance: " + bankAccountModel.getAccountBalance() + " User: " + bankAccountModel.getUserName();
        LOGGER.info("converting '{}' into '{}'", bankAccountModel, accounts);
        return accounts;
    }
}
