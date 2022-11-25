package baz.practice.wizeline.learningjavamaven.batch;

import baz.practice.wizeline.learningjavamaven.factory.CuentaAhorro;
import baz.practice.wizeline.learningjavamaven.factory.CuentaPlatinum;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.factory.Cuenta;
import baz.practice.wizeline.learningjavamaven.factory.CuentaNomina;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Processor for BankAccountJob.
 */
public class BankAccountItemProcessor implements ItemProcessor<BankAccountDTO, String> {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BankAccountItemProcessor.class);

    @Override
    public String process(BankAccountDTO bankAccountDTO) throws Exception {
        String accounts = "";
        String accountType = bankAccountDTO.getAccountType().toString();
        switch(accountType){
            case "NOMINA":
                Cuenta cuenta = new CuentaNomina();
                cuenta.mensaje();
                accounts = "Country: " + bankAccountDTO.getCountry() + " Account Name: " + bankAccountDTO.getAccountName()
                    + " Account Type: " + accountType + " Account Balance: " + bankAccountDTO.getAccountBalance() + " User: " + bankAccountDTO.getUserName();
                LOGGER.info("converting '{}' into '{}'", bankAccountDTO, accounts);

            case "AHORRO":
                cuenta = new CuentaAhorro();
                cuenta.mensaje();
                accounts = "Country: " + bankAccountDTO.getCountry() + " Account Name: " + bankAccountDTO.getAccountName()
                    + " Account Type: " + accountType + " Account Balance: " + bankAccountDTO.getAccountBalance() + " User: " + bankAccountDTO.getUserName();
                LOGGER.info("converting '{}' into '{}'", bankAccountDTO, accounts);

             case "PLATINUM":
                 cuenta = new CuentaPlatinum();
                 cuenta.mensaje();
                accounts = "Country: " + bankAccountDTO.getCountry() + " Account Name: " + bankAccountDTO.getAccountName()
                    + " Account Type: " + accountType + " Account Balance: " + bankAccountDTO.getAccountBalance() + " User: " + bankAccountDTO.getUserName();
                LOGGER.info("converting '{}' into '{}'", bankAccountDTO, accounts);
        }
        /*
        String accounts = "Country: " + bankAccountDTO.getCountry() + " Account Name: " + bankAccountDTO.getAccountName()
                + " Account Type: " + accountType + " Account Balance: " + bankAccountDTO.getAccountBalance() + " User: " + bankAccountDTO.getUserName();
        LOGGER.info("converting '{}' into '{}'", bankAccountDTO, accounts);
         */
        return accounts;
    }
}