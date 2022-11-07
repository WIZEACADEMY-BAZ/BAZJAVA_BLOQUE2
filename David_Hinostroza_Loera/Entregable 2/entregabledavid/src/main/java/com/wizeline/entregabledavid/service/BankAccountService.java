package com.wizeline.entregabledavid.service;

import com.wizeline.entregabledavid.model.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    /**
     * Gets accounts.
     *
     * @return todas las cuentas existentes en formato de lista.
     */
    List<BankAccountDTO> getAccounts();
    /**
     * Gets account details.
     *
     * @param user      nombre de usuario.
     * @param lastUsage último uso de la cuenta.
     * @return detalles de una sola cuenta.
     */
    BankAccountDTO getAccountDetails(String user, String lastUsage);

    void deleteAccounts();

    List<BankAccountDTO> getAccountByUser(String user);

    BankAccountDTO putAccountByUser(String user);
}
