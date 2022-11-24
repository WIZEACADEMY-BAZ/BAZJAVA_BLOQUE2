package com.wizeline.maven.learningjavagmaven.batch;

import com.wizeline.maven.learningjavagmaven.FactoryMeth.Cuenta;
import com.wizeline.maven.learningjavagmaven.FactoryMeth.Mexico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.FactoryMeth.France;
import com.wizeline.maven.learningjavagmaven.FactoryMeth.UnitedStates;

/**
 * Processor for BankAccountJob.
 */
public class BankAccountItemProcessor implements ItemProcessor<BankAccountModel, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountItemProcessor.class);

    /**
     *patron de creacion
     * Factory Method
     */
    @Override
    public String process(BankAccountModel bankAccountModel) throws Exception {
        String accountType = bankAccountModel.getAccountType().toString();

        String accounts="";
        Cuenta cuenta;
        String country=bankAccountModel.getCountry().toString();
        switch (country) {
            case "Mexico":
                cuenta = new Mexico();
                cuenta.mensaje();
                accounts = "Country: " + bankAccountModel.getCountry() + " Account Name: " + bankAccountModel.getAccountName()
                    + " Account Type: " + accountType + " Account Balance: " + bankAccountModel.getAccountBalance() + " User: " + bankAccountModel.getUser();
                LOGGER.info("converting '{}' into '{}'", bankAccountModel, accounts);
            case "France":
                cuenta = new France();
                cuenta.mensaje();
                accounts = "Country: " + bankAccountModel.getCountry() + " Account Name: " + bankAccountModel.getAccountName()
                        + " Account Type: " + accountType + " Account Balance: " + bankAccountModel.getAccountBalance() + " User: " + bankAccountModel.getUser();
                LOGGER.info("converting '{}' into '{}'", bankAccountModel, accounts);
            case "UnitedStates":
                cuenta = new UnitedStates();
                cuenta.mensaje();
                accounts  = "Country: " + bankAccountModel.getCountry() + " Account Name: " + bankAccountModel.getAccountName()
                        + " Account Type: " + accountType + " Account Balance: " + bankAccountModel.getAccountBalance() + " User: " + bankAccountModel.getUser();
                LOGGER.info("converting '{}' into '{}'", bankAccountModel, accounts);
        }

        return accounts;
    }
}