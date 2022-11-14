package com.wizeline.gradle.learningjavagradle.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountNominaRepository;
import com.wizeline.gradle.learningjavagradle.utils.Utils;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.NotificationsException;

@Service
public class BankAccountNominaServiceImpl implements BankAccountNominaService{
	private static final Logger LOGGER = Logger.getLogger(BankAccountNominaServiceImpl.class.getName());
	@Autowired
	BankingAccountNominaRepository dao;
	
	@Override
	public ResponseEntity<BankAccountNomina> obtenerCuenta(String user) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
		Instant inicioDeEjecucion = Instant.now();
		/*
		 * Uso de Optional
		 */
		Optional<BankAccountNomina> cuenta = dao.obtenerCuenta(user);
		if(cuenta.isPresent()) {			
			this.HiloParaEspera(5);
			BankAccountNomina response= Utils.cifrado(cuenta.get());	
			Instant finalDeEjecucion = Instant.now();
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
			LOGGER.info("Tiempo de respuesta: ".concat(total));
			return ResponseEntity.ok(response);
		}else {
			/*
			 * Excepcion de creacion propia
			 */
	    	throw new NotificationsException("Error al obtener cuenta", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<String> createNomina(BankAccountNomina request) {
		Instant inicioDeEjecucion = Instant.now();
		if(dao.insertaCuenta(request)) {
			Instant finalDeEjecucion = Instant.now();
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
			LOGGER.info("Tiempo de respuesta: ".concat(total));
			return ResponseEntity.ok("Cuenta creada correctamente");
		}else {
	    	throw new NotificationsException("Error al crear la cuenta", HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseEntity<?> updateNomina(BankAccountNomina request) {
		Instant inicioDeEjecucion = Instant.now();
		if(dao.updateCuenta(request)) {
			Instant finalDeEjecucion = Instant.now();
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
			LOGGER.info("Tiempo de respuesta: ".concat(total));
			return ResponseEntity.ok("Cuenta actualizada correctamente");
		}else {
	    	throw new NotificationsException("Error al actualizar la cuenta", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> deleteNomina(Long accountNumber) {
		Instant inicioDeEjecucion = Instant.now();
		if(dao.deleteCuenta(accountNumber)) {
			Instant finalDeEjecucion = Instant.now();
			String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
			LOGGER.info("Tiempo de respuesta: ".concat(total));
			return ResponseEntity.ok("Cuenta eliminada correctamente");
		}else {
	    	throw new NotificationsException("Error al eliminar la cuenta", HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
    private void HiloParaEspera(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            LOGGER.info("Entra al hilo de espera");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


}
