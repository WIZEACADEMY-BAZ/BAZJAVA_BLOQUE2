package com.wizeline.maven.learninjavamaven.service;

import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDTO> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO;

    List<BankAccountDTO> getAccountByUser(String user);

    BankAccountDTO getAccountDetails(String user, String lastUsage);

    BankAccountDTO getAccountDetails(String user, String lastUsage, Country country);

    void deleteAccounts();

    BankAccountDTO findByUser(String user);

    BankAccountDTO save(BankAccountDTO bankAccountDTO);

    List<BankAccountDTO> encryptedAccounts();
}
