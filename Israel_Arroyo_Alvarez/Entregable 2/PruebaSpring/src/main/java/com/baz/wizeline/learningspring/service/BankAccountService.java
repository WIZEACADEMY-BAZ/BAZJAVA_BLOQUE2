package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDTO> getAccounts();

    List<BankAccountDTO> accountsEncrypt();

    List<BankAccountDTO> getAccountsActives();

    List<BankAccountDTO> getAccountsFunctional();

    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);
}
