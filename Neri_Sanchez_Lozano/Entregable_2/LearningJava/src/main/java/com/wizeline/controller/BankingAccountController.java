package com.wizeline.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.service.BankAccountService;
import com.wizeline.service.BankAccountServiceImpl;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("/api")
@RestController
public class BankingAccountController {
	
	private static final Logger log = Logger.getLogger(BankingAccountController.class.getName());
	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
	
	@Autowired
	BankAccountService bankAccountService;
	
	@DeleteMapping("/deleteAccounts")
	public ResponseEntity<String> deleteAccounts() {
		bankAccountService.deleteAccounts();
		return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/getAccountByUser")
	   public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
	       log.info(msgProcPeticion);
	       Instant inicioDeEjecucion = Instant.now();
	       log.info("LearningJava - Procesando peticion HTTP de tipo GET");
	       List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

	       Instant finalDeEjecucion = Instant.now();

	       log.info("LearningJava - Cerrando recursos ...");
	       String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
	       log.info("Tiempo de respuesta: ".concat(total));

	       HttpHeaders responseHeaders = new HttpHeaders();
	       responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
	       return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
	   }
	
	
	@GetMapping("/getAccounts")
	public ResponseEntity<List<BankAccountDTO>> getAccounts() {
		log.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();
		log.info("LearningJava - Procesando peticion HTTP de tipo GET");
		List<BankAccountDTO> accounts = bankAccountService.getAccounts();
		Instant finalDeEjecucion = Instant.now();
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		log.info("Tiempo de respuesta: ".concat(total));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAccountsGroupByType")
	public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
		log.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        BankAccountService bankAccountBO = new BankAccountServiceImpl();
        
        log.info("LearningJava - Procesando peticion HTTP GET");
        List<BankAccountDTO> accounts = bankAccountBO.getAccounts();
        
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        
        Instant finalDeEjecucion = Instant.now();
        log.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        log.info("Tiempo de respuesta: ".concat(total));
        
        return new ResponseEntity<Map<String,List<BankAccountDTO>>>(groupedAccounts, HttpStatus.OK);

	}
	
	@PreAuthorize("hasRole('GUEST')")
	@GetMapping("/sayHello")
	  public ResponseEntity<String> sayHelloGuest() {
	     return new ResponseEntity<>("Hola invitado !!", HttpStatus.OK);
	  }

	@PostMapping("/postAccount")
	  public ResponseEntity<String> postAccount(@RequestBody BankAccountDTO account) {
		bankAccountService.postAccount(account);
		return new ResponseEntity<>(HttpStatus.OK);
	  }

}
