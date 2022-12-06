package com.wizeline.baz.model.response;

import java.time.LocalDateTime;

import com.wizeline.baz.enums.BankAccountType;

public class BankAccountDetailsResponse extends BaseResponseDTO {
	private long accountNumber;
    private String accountAlias;    
    private double accountBalance;
    private BankAccountType accountType;
    private boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime lastUsage;
    
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getLastUsage() {
		return lastUsage;
	}
	public void setLastUsage(LocalDateTime lastUsage) {
		this.lastUsage = lastUsage;
	}
    
    
}
