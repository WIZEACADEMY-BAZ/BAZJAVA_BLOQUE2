package com.wizeline.learningspring.service;

import java.util.List;

import com.wizeline.learningspring.model.BankAccountDTO;

public interface BankAccountService {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
    void deleteAccounts(Long id);
    void updateAccount(Long accountNumber, BankAccountDTO request);
    List<BankAccountDTO> getAccountByUser(String user);

    List<BankAccountDTO> encryptedAccounts();

}
