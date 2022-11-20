package com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories;

import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts.Account;
import org.springframework.stereotype.Component;

public interface AccountHolderFactory {
  Account createAccount();
}
