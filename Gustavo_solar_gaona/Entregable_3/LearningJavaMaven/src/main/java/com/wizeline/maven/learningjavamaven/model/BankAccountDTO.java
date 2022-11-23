package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("bankAccountCollection")
public class BankAccountDTO {
  @Id
  private long accountNumber;
  private String accountName;
  private String userName;
  private double accountBalance;
  private AccountType accountType;
  private String country;
  private boolean accountActive;
  private LocalDateTime creationDate;
  private LocalDateTime lastUsage;


  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDateTime getLastUsage() {
    return LocalDateTime.now();
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
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

  public void setAccountBalance(double accountBalance) {
    this.accountBalance = accountBalance;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setAccountActive(boolean accountActive) {
    this.accountActive = accountActive;
  }
}
