package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;


import java.util.List;

public interface BankAccountService {

    List<BankAccountDTO> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO

    BankAccountDTO getAccountDetails(String user, String lastUsage);
    BankAccountDTO getAccountDetails(String user);

    //BankAccountDTO getAccountDetails(String user);

    void  deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);
}
