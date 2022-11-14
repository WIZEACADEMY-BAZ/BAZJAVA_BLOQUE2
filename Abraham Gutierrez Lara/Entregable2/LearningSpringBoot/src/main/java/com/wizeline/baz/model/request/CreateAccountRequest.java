package com.wizeline.baz.model.request;

import com.wizeline.baz.enums.BankAccountType;

public class CreateAccountRequest {
	private String userId;
	private BankAccountType accountType;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BankAccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(BankAccountType accountType) {
		this.accountType = accountType;
	}
}
