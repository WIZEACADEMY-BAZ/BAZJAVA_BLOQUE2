package com.wizeline.baz.repository;

import com.wizeline.baz.enums.BankAccountType;
import com.wizeline.baz.model.BankAccountDTO;

public interface BankAccountFactory {
	
	BankAccountDTO createBankAccount(String userId, BankAccountType accountType);
}
