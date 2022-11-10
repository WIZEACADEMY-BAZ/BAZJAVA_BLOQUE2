package com.wizeline.baz.model.request;

import com.wizeline.baz.enums.AccountType;

public class CreateAccountRequest {
	private String userId;
	private AccountType accountType;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}
