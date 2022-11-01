package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
}
