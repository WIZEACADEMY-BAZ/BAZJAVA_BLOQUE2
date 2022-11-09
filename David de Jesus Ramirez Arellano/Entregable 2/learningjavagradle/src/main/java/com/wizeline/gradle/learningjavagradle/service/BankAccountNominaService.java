package com.wizeline.gradle.learningjavagradle.service;

import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

import org.springframework.http.ResponseEntity;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

public interface BankAccountNominaService {

	ResponseEntity<BankAccountNomina> obtenerCuenta(String user) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException;

	ResponseEntity<String> createNomina(BankAccountNomina request);

	ResponseEntity<?> updateNomina(BankAccountNomina request);

	ResponseEntity<?> deleteNomina(Long accountNumber);
}
