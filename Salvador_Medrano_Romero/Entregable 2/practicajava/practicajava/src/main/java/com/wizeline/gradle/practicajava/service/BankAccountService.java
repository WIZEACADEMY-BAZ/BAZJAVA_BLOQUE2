package com.wizeline.gradle.practicajava.service;

import java.util.List;

import com.wizeline.gradle.practicajava.model.BankAccountDTO;

public interface BankAccountService {
	List<BankAccountDTO> getAccounts();
	BankAccountDTO getAccountDetails(String user, String lastUsage);
	void deleteAccounts();
	List<BankAccountDTO> getAccountsByUser(String user);
}
