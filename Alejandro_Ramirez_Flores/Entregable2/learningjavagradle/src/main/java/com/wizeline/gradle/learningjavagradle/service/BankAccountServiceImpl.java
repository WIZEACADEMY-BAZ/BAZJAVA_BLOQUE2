package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.*;

public class BankAccountServiceImpl implements BankAccountService {
    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7)));
        accountDTOList.add(buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2)));
        accountDTOList.add(buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4)));
        return accountDTOList;
    }
    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage){
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    //creacion de tipos de datos BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}
