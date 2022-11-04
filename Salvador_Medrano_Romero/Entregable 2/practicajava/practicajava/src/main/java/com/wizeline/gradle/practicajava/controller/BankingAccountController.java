package com.wizeline.gradle.practicajava.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.gradle.practicajava.model.BankAccountDTO;
import com.wizeline.gradle.practicajava.service.BankAccountService;



@RestController
@RequestMapping("/apiBank")
public class BankingAccountController {
	
	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	private static String msgProcPeticion = "LearningJava - Inicia procesamiento de petici�n...";
	
	@Autowired
	BankAccountService bankAccountService;

	@DeleteMapping("/deleteAccounts")
	public ResponseEntity<String> deleteAccounts(){
		bankAccountService.deleteAccounts();
		return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	}
	
	 @PreAuthorize("hasRole('USER')")
	 @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user){
		LOGGER.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		List<BankAccountDTO> accounts = bankAccountService.getAccountsByUser(user);
		
		Instant finalDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));
		
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		
		return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
		
	}
	
	@GetMapping("/getAccounts")
	public ResponseEntity<String> getAccounts(){
		bankAccountService.getAccounts();
		return new ResponseEntity<>("All accounts created", HttpStatus.OK);
	}
	  
	  @PreAuthorize("hasRole('ADMIN')")
	  @GetMapping(value = "/getAccountsGroupByType")
	  public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
		return null;
	     // Implementaci�n
	  }

	  @PreAuthorize("hasRole('GUEST')")
	  @GetMapping("/sayHello")
	  public ResponseEntity<String> sayHelloGuest() {
	     return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
	  }
}
