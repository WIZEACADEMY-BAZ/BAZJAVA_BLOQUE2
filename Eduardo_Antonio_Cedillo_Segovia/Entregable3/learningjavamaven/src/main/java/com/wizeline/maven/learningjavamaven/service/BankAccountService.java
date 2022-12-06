package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;

import java.util.List;
import java.util.Optional;


public interface BankAccountService {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);

    BankAccountDTO getAccountDetails(String user);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);
    //Declarando el metodo del optional
    Optional<BankAccountDTO> getAccountByAccountNumber(long accountNumber);
    BankAccountDTO putCountry(String country);

}
