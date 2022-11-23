package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface AccountService {

  BankAccountDTO getAccount(String user, String password, String date);

  List<BankAccountDTO> getAccounts();

  List<BankAccountDTO> getAccountByName(String name);

  List<BankAccountDTO> getAccountByUser(String userString);

  Map<String, List<BankAccountDTO>> getAccountsGroupByType();

  List<BankAccountDTO> getEncryptedAccounts();
}
