package com.wizeline.gradle.learningjavagradle.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

public interface CuentaNomService {

	ResponseEntity<BankAccountNomina> obtenerCuenta(String user);

	ResponseEntity<?> createNomina(BankAccountNomina request);

	ResponseEntity<?> updateNomina(BankAccountNomina request);

	ResponseEntity<?> deleteNomina(Long accountNumber);
}
