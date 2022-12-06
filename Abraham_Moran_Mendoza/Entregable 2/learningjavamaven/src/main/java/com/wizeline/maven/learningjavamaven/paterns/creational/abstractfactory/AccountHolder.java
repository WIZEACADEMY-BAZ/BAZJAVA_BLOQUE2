package com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory;

import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories.AccountHolderFactory;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.springframework.stereotype.Component;

public class AccountHolder {
  private Account account;

  public AccountHolder(AccountHolderFactory factory){
    account = factory.createAccount();
  }

  public BankAccountDTO getInfo(){
    return account.getInfo();
  }
}
