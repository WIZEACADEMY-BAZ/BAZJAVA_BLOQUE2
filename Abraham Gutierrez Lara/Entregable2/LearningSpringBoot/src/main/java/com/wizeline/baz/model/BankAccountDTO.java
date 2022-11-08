package com.wizeline.baz.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wizeline.baz.enums.AccountType;
import com.wizeline.baz.enums.Country;

/**
 * @author Abraham GL
 *
 */
@Document("BankAccounts")
public class BankAccountDTO {
	@Id
	private long accountNumber;
	private String userId;
    private String accountAlias;    
    private double accountBalance;
    private AccountType accountType;
    private Country country;
    private boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime lastUsage;
    
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
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
