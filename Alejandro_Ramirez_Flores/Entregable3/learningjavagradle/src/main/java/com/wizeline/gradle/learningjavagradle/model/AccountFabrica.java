package com.wizeline.gradle.learningjavagradle.model;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import static com.wizeline.gradle.learningjavagradle.enums.AccountType.AHORRO;
import static com.wizeline.gradle.learningjavagradle.enums.AccountType.NOMINA;
import static com.wizeline.gradle.learningjavagradle.enums.AccountType.PLATA;

public class AccountFabrica {
	

	public Account getCuenta(AccountType accountType) {
		
		if(NOMINA.equals(accountType)) {
			return new Nomina();
		}
		
		if(PLATA.equals(accountType)) {
			return new Plata();
		}
		
		if(AHORRO.equals(accountType)) {
			return new Ahorro();
		}
		
		return null;
	}

}
