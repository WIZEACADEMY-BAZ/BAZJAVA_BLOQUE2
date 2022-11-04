package com.wizeline.BO;

import java.util.List;

import com.wizeline.DTO.BankAccountDTO;
public interface BankAccountBO {


    List<BankAccountDTO> getAccounts();


     BankAccountDTO getAccountDetails(String user, String lastUsage);
    //BankAccountDTO getAccountDetails(String user);
}
