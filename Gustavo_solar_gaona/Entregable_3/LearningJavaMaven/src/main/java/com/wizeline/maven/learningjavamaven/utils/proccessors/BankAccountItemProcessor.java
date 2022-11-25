package com.wizeline.maven.learningjavamaven.utils.proccessors;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class BankAccountItemProcessor implements ItemProcessor<BankAccountDTO, String > {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountItemProcessor.class);

    @Override
    public String process(BankAccountDTO bankAccountDTO) throws Exception {
        String accoutType = bankAccountDTO.getAccountType().toString();
        String acocounts = "Country: " + bankAccountDTO.getCountry() + " Account Name: " + bankAccountDTO.getAccountName();
        LOGGER.info("converting '{}' into '{}'", bankAccountDTO, acocounts);
        return acocounts;
    }
}
