package com.wizeline.gradle.learningjavagradle.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountNominaRepository;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.NotificationsException;

@Service
public class CuentaNomServiceImpl implements CuentaNomService{

	@Autowired
	BankingAccountNominaRepository dao;
	
	@Override
	public ResponseEntity<BankAccountNomina> obtenerCuenta(String user) {
		/*
		 * Uso de Optional
		 */
		Optional<BankAccountNomina> cuenta = dao.obtenerCuenta(user);
		if(cuenta.isPresent()) {
			return ResponseEntity.ok(cuenta.get());
		}else {
			
	    	throw new NotificationsException("Error al obtener cuenta", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> createNomina(BankAccountNomina request) {
		if(dao.insertaCuenta(request)) {
			return ResponseEntity.ok("Cuenta creada correctamente");
		}else {
	    	throw new NotificationsException("Error al crear la cuenta", HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> updateNomina(BankAccountNomina request) {
		if(dao.updateCuenta(request)) {
			return ResponseEntity.ok("Cuenta actualizada correctamente");
		}else {
	    	throw new NotificationsException("Error al actualizar la cuenta", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> deleteNomina(Long accountNumber) {
		if(dao.deleteCuenta(accountNumber)) {
			return ResponseEntity.ok("Cuenta eliminada correctamente");
		}else {
	    	throw new NotificationsException("Error al eliminar la cuenta", HttpStatus.BAD_REQUEST);
		}
	}
}
	
