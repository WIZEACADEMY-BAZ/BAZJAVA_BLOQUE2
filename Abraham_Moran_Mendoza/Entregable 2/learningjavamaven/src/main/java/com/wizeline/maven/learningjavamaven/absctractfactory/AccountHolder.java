package com.wizeline.maven.learningjavamaven.absctractfactory;

import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.absctractfactory.factories.AccountHolderFactory;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;

public class AccountHolder {
  private Account account;

  public AccountHolder(AccountHolderFactory factory){
    account = factory.createAccount();
  }

  public BankAccountDTO getInfo(){
    return account.getInfo();
  }
}
