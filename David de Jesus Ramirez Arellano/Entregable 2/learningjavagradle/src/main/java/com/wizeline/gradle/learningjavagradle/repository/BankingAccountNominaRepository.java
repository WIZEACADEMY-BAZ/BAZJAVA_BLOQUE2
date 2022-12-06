package com.wizeline.gradle.learningjavagradle.repository;

import java.util.Optional;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;

public interface BankingAccountNominaRepository{

	Optional<BankAccountNomina> obtenerCuenta(String user);

	boolean insertaCuenta(BankAccountNomina request);

	boolean updateCuenta(BankAccountNomina request);

	boolean deleteCuenta(Long accountNumber);

}
