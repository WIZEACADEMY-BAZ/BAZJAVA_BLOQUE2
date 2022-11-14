package com.wizeline.maven.learningjavamaven.absctractfactory.factories;

import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.BanorteAccount;

public class BanorteFactory implements AccountHolderFactory{
  @Override
  public Account createAccount() {
    return new BanorteAccount();
  }
}
