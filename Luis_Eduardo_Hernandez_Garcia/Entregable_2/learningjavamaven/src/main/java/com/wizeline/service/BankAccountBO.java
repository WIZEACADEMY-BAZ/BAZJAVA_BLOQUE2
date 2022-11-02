package com.wizeline.service;

import com.wizeline.model.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {
    BankAccountDTO getAccountDetails(String user, String lastUsage);
    List<BankAccountDTO> getAccounts();
}
