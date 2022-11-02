/*
 * Copyright (c) 2022 Nextiva, Inc. to Present.
 * All rights reserved.
 */


package com.cursojava.learning2.service;

import com.cursojava.learning2.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cursojava.learning2.enums.Country;
import com.cursojava.learning2.model.BankAccountDTO;

/**
 * Class Description goes here.
 * Created by jose.vazquez on 07/09/22
 */
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
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    // Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(Utils.randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(Utils.randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(Utils.randomBalance());
        bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
        bankAccountDTO.setCountry(Utils.getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}
