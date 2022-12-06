package com.wizeline.gradle.learningjavagradle.model;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wizeline.gradle.learningjavagradle.controller.BankingAccountController;
import com.wizeline.gradle.learningjavagradle.enums.AccountType;

/**
 * @author 1059833
 *
 */
@Document("bankAccountCollection")
public class BankAccountDTO {

	private static final Logger LOGGER = Logger.getLogger(BankAccountDTO.class.getName());
	
    private long accountNumber;

    private String accountName;

    private String user;

    private double accountBalance;

    private AccountType accountType;

    private String country;

    private boolean accountActive;

    private LocalDateTime creationDate;

    private LocalDateTime lastUsage;
    
    /*
     * Metodo que implementa un generico
     */
	public <T extends Number> void deposito(T cantidad) {
		double balance = getAccountBalance();
		balance += cantidad.doubleValue();
		setAccountBalance(balance);
		LOGGER.info("Cantidad depositada $" + cantidad);
	}
    
    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAccountActive() {
        return accountActive;
    }

    public void setAccountActive(boolean accountActive) {
        this.accountActive = accountActive;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUsage() {
        return LocalDateTime.now();
    }

	public void setLastUsage(LocalDateTime lastUsage) {
		this.lastUsage = lastUsage;
	}


}
