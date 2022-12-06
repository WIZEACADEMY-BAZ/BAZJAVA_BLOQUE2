package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountModel;

import java.util.List;

public interface BankAccountService {
    List<BankAccountModel> getAccounts();
    BankAccountModel getAccountDetails(String user, String lastUsage);
    void deleteAccounts();
    List<BankAccountModel> getAccountByUser(String user);
}
