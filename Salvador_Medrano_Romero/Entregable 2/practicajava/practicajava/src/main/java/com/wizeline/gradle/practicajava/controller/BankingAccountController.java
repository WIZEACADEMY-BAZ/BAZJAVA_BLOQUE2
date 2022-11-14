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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.gradle.practicajava.client.AccountsJSONClient;
import com.wizeline.gradle.practicajava.model.BankAccountDTO;
import com.wizeline.gradle.practicajava.model.Post;
import com.wizeline.gradle.practicajava.service.BankAccountService;
import com.wizeline.gradle.practicajava.utils.Utils;

@RestController
@RequestMapping("/apiBank")
public class BankingAccountController {
	
	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	private static String msgProcPeticion = "LearningJava - Inicia procesamiento de petici�n...";
	
	@Autowired
	BankAccountService bankAccountService;

//	@Autowired
//	AccountsJSONClient accountsJSONClient;
	
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
	  
//	  @GetMapping("/getExternalUser/{userId}")
//		 public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {
//
//				 Post postTest = accountsJSONClient.getPostById(userId);
//				 LOGGER.info("Getting post userId..." +postTest.getUserId());
//				 LOGGER.info("Getting post body..." +postTest.getBody());
//				 LOGGER.info("Getting post title..." +postTest.getTitle());
//				 postTest.setUserId("External user "+ Utils.randomAcountNumber());
//				 postTest.setBody("No info in accountBalance since it is an external user");
//				 postTest.setTitle("No info in title since it is an external user");
//				 LOGGER.info("Setting post userId..." +postTest.getUserId());
//				 LOGGER.info("Setting post body..." +postTest.getBody());
//				 LOGGER.info("Setting post title...."+postTest.getTitle());
//				 HttpHeaders responseHeaders = new HttpHeaders();
//				 responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
//				 return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
//		 }
}
