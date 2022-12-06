package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;

import java.util.List;
import java.util.Optional;

//Revisión: Uso de por lo menos 1 Interfaz Funcional
public interface BankAccountService {

  List<BankAccountDTO> getAccountsLocal();

  List<BankAccountDTO> getAccounts();

  BankAccountDTO getAccountDetails(String user, String lastUsage);

  void deleteAccounts();

  List<BankAccountDTO> getAccountByUser(String user);

  Optional<BankAccountDTO> changeCountry(long accountNumber, String country);

  BankAccountDTO createAccount(BankAccountDTO bankAccountDTO);


}
