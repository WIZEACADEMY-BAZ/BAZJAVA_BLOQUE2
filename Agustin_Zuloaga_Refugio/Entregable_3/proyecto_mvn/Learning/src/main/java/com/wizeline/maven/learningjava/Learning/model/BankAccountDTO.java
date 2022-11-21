package com.wizeline.maven.learningjava.Learning.model;

import java.time.LocalDateTime;

import com.wizeline.maven.learningjava.Learning.enums.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("bankAccountCollection")
public class BankAccountDTO {

    @Id
    private Long accountNumber;

    private String accountName;

    private String userName;

    private double accountBalance;

    private AccountType accountType;

    private String country;

    private boolean accountActive;

    private LocalDateTime creationDate;

    private LocalDateTime lastUsage;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setLastUsage(LocalDateTime lastUsage) { this.lastUsage = lastUsage; }
}