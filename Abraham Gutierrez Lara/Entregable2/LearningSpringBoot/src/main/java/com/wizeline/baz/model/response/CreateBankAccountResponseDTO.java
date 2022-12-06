package com.wizeline.baz.model.response;

import com.wizeline.baz.enums.BankAccountType;

public class CreateBankAccountResponseDTO extends BaseResponseDTO {
	private long accountNumber;
    private String accountAlias;    
    private double accountBalance;
    private BankAccountType accountType;
    
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountAlias() {
		return accountAlias;
	}
	public void setAccountAlias(String accountAlias) {
		this.accountAlias = accountAlias;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public BankAccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(BankAccountType accountType) {
		this.accountType = accountType;
	}
}
