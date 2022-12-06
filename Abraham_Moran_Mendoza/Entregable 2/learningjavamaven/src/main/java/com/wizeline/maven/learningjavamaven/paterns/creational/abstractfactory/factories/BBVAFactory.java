package com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories;

import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.BBVAAccount;

public class BBVAFactory implements AccountHolderFactory{
  @Override
  public Account createAccount() {
    return new BBVAAccount();
  }
}
