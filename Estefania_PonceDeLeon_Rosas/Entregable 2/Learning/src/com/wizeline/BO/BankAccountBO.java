package com.wizeline.BO;

import com.wizeline.DTO.BankAccountDTO;

import java.util.List;

public interface BankAccountBO {

    List<BankAccountDTO> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO
    BankAccountDTO getAccountDetails(String user, String lastUsage);

}