package com.wizeline.gradle.learningjavagradle.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.gradle.learningjavagradle.client.AccountsJSONClient;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.Post;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.utils.Utils;

@RestController
@RequestMapping("/api/account")
public class BankingAccountController {
	private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
	 @Autowired
	    private BankAccountService bankAccountService;
	 
	 @Autowired
	  private AccountsJSONClient accountsJSONClient;
	 
	 @Autowired
	 private KafkaTemplate<Object, Object> template;
	 
	    @GetMapping(value = "/getUserAccount", produces = "application/json")
	    public BankAccountDTO getUserAccount(@RequestParam String user, String date){
	        return this.bankAccountService.getAccountDetails(user,date);
	    }
	    @GetMapping(value = "/getAccounts", produces = "application/json")
	    public List<BankAccountDTO> getAccounts(){
	        return this.bankAccountService.getAccounts();
	    }

	    @GetMapping(value = "/getAccountsGroupByType")
	    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
	        Instant inicioDeEjecucion = Instant.now();
	        List<BankAccountDTO> accounts = bankAccountService.getAccounts();	        
	        Map<String, List<BankAccountDTO>> groupedAccounts;
	        /*
	         * Implementacion de interfaz funcional 
	         * Implementacion de una lambda asignada a una interfaz funcional
	         */
	        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
	        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
	        Instant finalDeEjecucion = Instant.now();
	        String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
	        LOGGER.info("Tiempo de respuesta: ".concat(total));

	        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
	    }
	    
	    @GetMapping("/getAccountByUser")
	    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
	        Instant inicioDeEjecucion = Instant.now();	        
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
	    
	    @GetMapping("/sayHello")
	    public ResponseEntity<String> sayHelloGuest() {
	       return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
	    }
	    
	    /*
	     * Endpoint que utiliza un metodo con genericos
	     */
	    @GetMapping("/deposito")
	    public ResponseEntity<BankAccountDTO> depositar(@RequestParam String nombre,@RequestParam float cantidad) {
	    	BankAccountDTO response = null;
	    	try {
	    		 response=bankAccountService.getAccountByName(nombre);
	    		 if(Objects.nonNull(response)) {
	    			 LOGGER.info("Cantidad antes "+response.getAccountBalance());
	    			 response.deposito(cantidad);
	    			 LOGGER.info("Cantidad despues "+response.getAccountBalance());
	    		 }
	    	}catch(Exception e) {
	    		LOGGER.info(e.getMessage());
	    	}
	       return new ResponseEntity<>(response, HttpStatus.OK);
	    }
		 @GetMapping("/getExternalUser/{userId}")
		 public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {
                
				 Post postTest = accountsJSONClient.getPostById(userId);
				 LOGGER.info("Getting post userId..." +postTest.getUserId());
				 LOGGER.info("Getting post body..." +postTest.getBody());
				 LOGGER.info("Getting post title..." +postTest.getTitle());
				 postTest.setUserId("External user "+Utils.randomAcountNumber());
				 postTest.setBody("No info in accountBalance since it is an external user");
				 postTest.setTitle("No info in title since it is an external user");
				 LOGGER.info("Setting post userId..." +postTest.getUserId());
				 LOGGER.info("Setting post body..." +postTest.getBody());
				 LOGGER.info("Setting post title...."+postTest.getTitle());
				 HttpHeaders responseHeaders = new HttpHeaders();
				 responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
				 return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
		 }   
		 
		 @PostMapping(path = "/send/{userId}")
		 public void sendUserAccount(@PathVariable Integer userId) {
		 		List<BankAccountDTO> accounts = bankAccountService.getAccounts();
		 		BankAccountDTO account = accounts.get(userId);
		 		this.template.send("useraccount-topic", account);
		 }
}
