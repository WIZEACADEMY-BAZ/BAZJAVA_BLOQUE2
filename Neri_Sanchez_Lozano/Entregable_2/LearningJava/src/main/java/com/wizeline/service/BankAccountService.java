package com.wizeline.service;

import java.util.List;

import com.wizeline.model.BankAccountDTO;

public interface BankAccountService {
	
	BankAccountDTO getAccountDetails(String user, String lastUsage);
	
	List<BankAccountDTO> getAccounts();
	
	void deleteAccounts();
	
	void postAccount(BankAccountDTO account);
	
	void updateAccount(BankAccountDTO account);
	
	List<BankAccountDTO> getAccountByUser(String user);

}
