package com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.accounts;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.utils.Utils;

public class AztecaAccount implements Account{
  @Override
  public BankAccountDTO getInfo() {
    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
    bankAccountDTO.setAccountName("Cuenta de azteca");
    return bankAccountDTO;
  }
}
