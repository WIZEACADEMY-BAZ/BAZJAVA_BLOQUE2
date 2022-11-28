package com.wizeline.gradle.learningjavagradle.Datos;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;

import java.time.LocalDateTime;

public class Datos {
    public static final UserDTO USER_001 = new UserDTO("Mateo", "Password1@");
    public static final UserDTO USER_002 = new UserDTO("Suleima", "Password2@");
    public static final BankAccountDTO CUENTA_001 = new BankAccountDTO(1L, "Nomina", "Mateo" , 0.00, AccountType.NOMINA, Country.MX.toString(), true, LocalDateTime.now(), null);
    public static final BankAccountDTO CUENTA_002 = new BankAccountDTO(1L, "Ahorro", "Suleima" , 0.00, AccountType.AHORRO, Country.MX.toString(), true, LocalDateTime.now(), null);
}
