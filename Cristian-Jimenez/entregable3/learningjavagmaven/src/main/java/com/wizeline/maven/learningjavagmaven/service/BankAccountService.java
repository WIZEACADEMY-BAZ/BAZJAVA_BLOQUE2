package com.wizeline.maven.learningjavagmaven.service;


import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import java.util.List;

public interface BankAccountService {


    List<BankAccountModel> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO ingresada en enums

    static BankAccountModel getAccountDetails(String user, String lastUsage) {
        return null;
    }

    void deleteAccounts();

    List<BankAccountModel> getAccountByUser(String user);

    BankAccountModel updateAccount(String accountName, String newAccountName);
}