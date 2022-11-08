package com.wizeline.gradle.learningjavagradle.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;

@RestController
@RequestMapping("/api/account")
public class BankingAccountController {
	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	 @Autowired
	    private BankAccountService bankAccountService;
	 
	    @GetMapping(value = "getUserAccount", produces = "application/json")
	    public BankAccountDTO getUserAccount(@RequestParam String user, String date){
	        return this.bankAccountService.getAccountDetails(user,date);
	    }
	    @GetMapping(value = "getAccounts", produces = "application/json")
	    public List<BankAccountDTO> getAccounts(){
	        return this.bankAccountService.getAccounts();
	    }

	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping(value = "getAccountsGroupByType", produces = "application/json")
	    public Map<String, List<BankAccountDTO>> getAccountsGroupByType(){
	        List<BankAccountDTO> accounts = this.bankAccountService.getAccounts();
	        Map<String, List<BankAccountDTO>> groupedAccounts;
	        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
	        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
	        return groupedAccounts;
	    }
	    
	    @PreAuthorize("hasRole('USER')")
	    @GetMapping("/getAccountByUser")
	    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
	        Instant inicioDeEjecucion = Instant.now();
	        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
	        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

	        Instant finalDeEjecucion = Instant.now();

	        LOGGER.info("LearningJava - Cerrando recursos ...");
	        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
	        LOGGER.info("Tiempo de respuesta: ".concat(total));

	        HttpHeaders responseHeaders = new HttpHeaders();
	        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
	        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
	    }
	    
	    @DeleteMapping("/deleteAccounts")
	    public ResponseEntity<String> deleteAccounts() {
	        bankAccountService.deleteAccounts();
	        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	    }
	    
	    @PreAuthorize("hasRole('GUEST')")
	    @GetMapping("/sayHello")
	    public ResponseEntity<String> sayHelloGuest() {
	       return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
	    }
}
