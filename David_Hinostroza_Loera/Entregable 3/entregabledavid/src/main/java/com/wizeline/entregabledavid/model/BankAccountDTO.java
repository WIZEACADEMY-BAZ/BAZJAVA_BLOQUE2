package com.wizeline.entregabledavid.model;

import com.wizeline.entregabledavid.enums.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("bankAccountCollection")
public class BankAccountDTO {
    @Id
    private long accountNumber;
    private String accountName;
    private String user;
    private Double accountBalance;
    private AccountType accountType;
    private String country;
    private boolean accountActive;
    private LocalDateTime creationDate;

    private LocalDateTime lastUsage;

    private String accountBalanceCifrado;

    public BankAccountDTO() {

    }

    public BankAccountDTO(long accountNumber, String accountName, String user, Double accountBalance, AccountType accountType, String country, boolean accountActive, LocalDateTime creationDate, LocalDateTime lastUsage, String accountBalanceCifrado) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.user = user;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
        this.country = country;
        this.accountActive = accountActive;
        this.creationDate = creationDate;
        this.lastUsage = lastUsage;
        this.accountBalanceCifrado = accountBalanceCifrado;
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

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
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

    public String getAccountBalanceCifrado() {
        return accountBalanceCifrado;
    }

    public void setAccountBalanceCifrado(String accountBalanceCifrado) {
        this.accountBalanceCifrado = accountBalanceCifrado;
    }
}
