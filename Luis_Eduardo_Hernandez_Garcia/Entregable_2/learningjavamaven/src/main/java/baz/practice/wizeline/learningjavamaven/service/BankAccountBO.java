package baz.practice.wizeline.learningjavamaven.service;

import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {
    BankAccountDTO getAccountDetails(String user, String lastUsage);
    List<BankAccountDTO> getAccounts();
}
