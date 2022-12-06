package com.wizeline.maven.learningjavagmaven.service;


import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import java.util.List;

public interface BankAccountService {
    List<BankAccountModel> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO ingresada en enums
    BankAccountModel getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountModel> getAccountByUser(String user);
}