package com.wizeline.maven.learningjava.Learning.service;

import java.util.List;

import com.wizeline.maven.learningjava.Learning.model.BankAccountDTO;

public interface BankAccountService {
    List<BankAccountDTO> getAccounts();

    List<BankAccountDTO> getAccountByUser(String user);

    void deleteAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
}


/// getAccounts implementar

