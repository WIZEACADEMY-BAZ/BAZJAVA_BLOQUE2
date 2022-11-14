package com.wizeline.maven.learningjavamaven.absctractfactory.factories;

import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.BBVAAccount;

public class BBVAFactory implements AccountHolderFactory{
  @Override
  public Account createAccount() {
    return new BBVAAccount();
  }
}
