package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//@Documented("bankAccountCollection")
@Document("bankAccountCollection")
public class BankAccountDTO {
    private long accountNumber;
    private String accountName;
    private String userName;
    //private String user;
    private double accountBalance;
    private AccountType accountType;
    private String country;
    private boolean accountActive;
    private LocalDateTime creationDate;
    //private String lastUsage;
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


    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUsage() {
        return LocalDateTime.now();
    }

    // Aqui implementaremos la programación funcional
    Map<String, List<BankAccountDTO>> groupedAccounts;
}