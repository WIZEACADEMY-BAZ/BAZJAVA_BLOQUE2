package com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories;

import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.BanorteAccount;

public class BanorteFactory implements AccountHolderFactory{
  @Override
  public Account createAccount() {
    return new BanorteAccount();
  }
}
