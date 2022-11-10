package com.wizeline.baz.LearningSpring.service;

import com.wizeline.baz.LearningSpring.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDTO> getAccounts();

    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);
}
