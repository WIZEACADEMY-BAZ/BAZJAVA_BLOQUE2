package com.wizeline.maven.learningjavamaven.absctractfactory.factories;

import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.Account;
import com.wizeline.maven.learningjavamaven.absctractfactory.accounts.AztecaAccount;

public class AztecaFactory implements AccountHolderFactory{

  @Override
  public Account createAccount() {
    return new AztecaAccount();
  }
}
