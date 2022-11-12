package com.wizeline.gradle.learningjavagradle.service;

import java.util.List;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

public interface BankAccountService {
	
	List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
    void deleteAccounts();
    List<BankAccountDTO> getAccountByUser(String user);

}
