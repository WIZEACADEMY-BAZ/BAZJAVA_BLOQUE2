package com.wizeline.maven.learninjavamaven.model;

import com.wizeline.maven.learninjavamaven.enums.AccountType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document("bankAccountCollection")
public class BankAccountDTO {
    private long accountNumber;
    private String accountName;
    private String user;
    private double accountBalance;

    public BankAccountDTO() {
    }

    //Sobrecarga de constructor
    public BankAccountDTO(long accountNumber, String accountName, String user, double accountBalance,
                          AccountType accountType, String country, boolean accountActive,
                          LocalDateTime lastUsage, LocalDateTime creationDate) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.user = user;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
        this.country = country;
        this.accountActive = accountActive;
        this.lastUsage = lastUsage;
        this.creationDate = creationDate;
    }

    private AccountType accountType;
    private String country;
    private boolean accountActive;
    private LocalDateTime lastUsage; // <- ultimo uso de la cuenta
    private LocalDateTime creationDate;

    public LocalDateTime getLastUsage() {
        return lastUsage;
    }

    public void setLastUsage(LocalDateTime lastUsage) {
        this.lastUsage = lastUsage;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccountDTO that = (BankAccountDTO) o;
        return accountNumber == that.accountNumber && Double.compare(that.accountBalance, accountBalance) == 0 && accountActive == that.accountActive && Objects.equals(accountName, that.accountName) && Objects.equals(user, that.user) && accountType == that.accountType && Objects.equals(country, that.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountName, user, accountBalance, accountType, country, accountActive);
    }

    @Override
    public String toString() {
        return "BankAccountDTO{" +
                "accountNumber=" + accountNumber +
                ", accountName='" + accountName + '\'' +
                ", user='" + user + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountType=" + accountType +
                ", country='" + country + '\'' +
                ", accountActive=" + accountActive +
                '}';
    }
}
