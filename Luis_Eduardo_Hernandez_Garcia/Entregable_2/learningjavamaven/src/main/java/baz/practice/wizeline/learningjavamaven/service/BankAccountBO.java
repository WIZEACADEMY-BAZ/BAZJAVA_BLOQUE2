package baz.practice.wizeline.learningjavamaven.service;

import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {
    BankAccountDTO getAccountDetails(String user, String lastUsage);

    BankAccountDTO getAccountDetails(String lastUsage);
    List<BankAccountDTO> getAccounts();

    void deleteAccounts();

    BankAccountDTO updateAccount(String accountName, String newAccountName);

    BankAccountDTO getAccountByName(String name);


    List<BankAccountDTO> getAccountByUser(String user);

}
