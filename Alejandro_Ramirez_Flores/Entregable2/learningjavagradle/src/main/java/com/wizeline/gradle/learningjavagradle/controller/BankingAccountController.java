package com.wizeline.gradle.learningjavagradle.controller;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.isDateFormatValid;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.isPasswordValid;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.randomAcountNumber;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.gradle.learningjavagrade.client.AccountsJSONClient;
import com.wizeline.gradle.learningjavagradle.configuration.SecurityConfig;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.Post;

@Tag(name = "Banking Account", description = "Contiene operaciones comunes realizadas en las cuentas.")

@RestController
@RequestMapping("/api")
public class BankingAccountController {
	
	@Value("${server.port}")
    private String port;

	private static final String SUCCESS_CODE = "OK000";


	@Autowired
	UserService userService;

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	CommonServices commonServices;
	
	@Autowired
	SecurityConfig securityConfig;
	
    AccountsJSONClient accountsJSONClient;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@GetMapping(value = "/getUserAccount")
	public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password,
			@RequestParam String date) {
		Instant inicioDeEjecucion = Instant.now();
		ResponseDTO response = new ResponseDTO();
		HttpHeaders responseHeaders = new HttpHeaders();
		String responseText = "";
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		if (isDateFormatValid(date)) {
			// Valida el password del usuario
			if (isPasswordValid(password)) {
				response = commonServices.login(user, password);
				if (response.getCode().equals(SUCCESS_CODE)) {
					BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
					Instant finalDeEjecucion = Instant.now();
					LOGGER.info("LearningJava - Cerrando recursos ...");
					String total = new String(
							String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis())
									.concat(" segundos."));
					LOGGER.info("Tiempo de respuesta: ".concat(total));
					return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
				}
			} else {
				Instant finalDeEjecucion = Instant.now();
				LOGGER.info("LearningJava - Cerrando recursos ...");
				String total = new String(
						String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis())
								.concat(" segundos."));
				LOGGER.info("Tiempo de respuesta: ".concat(total));
				responseText = "Password Incorrecto";
				return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
			}
		} else {
			responseText = "Formato de Fecha Incorrecto";
		}
		Instant finalDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));
		return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
	}

	@GetMapping("/getAccounts")
	public ResponseEntity<List<BankAccountDTO>> getAccounts() {
		Instant inicioDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");

		List<BankAccountDTO> accounts = bankAccountService.getAccounts();
		Instant finalDeEjecucion = Instant.now();

		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(
				String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		LOGGER.info("Tiempo de respuesta: ".concat(total));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

	}
	
	 @PreAuthorize("hasRole('ADMIN')")
	  @GetMapping(value = "/getAccountsGroupByType")
	  public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {

		        Instant inicioDeEjecucion = Instant.now();

		        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

		        // Aqui implementaremos la programación funcional
		        Map<String, List<BankAccountDTO>> groupedAccounts;
		        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
		        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
		        Instant finalDeEjecucion = Instant.now();

		        LOGGER.info("LearningJava - Cerrando recursos ...");
		        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
		        LOGGER.info("Tiempo de respuesta: ".concat(total));

		        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
	  }
	 

	private BankAccountDTO getAccountDetails(String user, String lastUsage) {
		return bankAccountService.getAccountDetails(user, lastUsage);
	}

	@DeleteMapping("/deleteAccounts")
	public ResponseEntity<String> deleteAccounts() {
		bankAccountService.deleteAccounts();
		return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
	       Instant inicioDeEjecucion = Instant.now();
	       LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
	       LOGGER.info(user);
	       List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

	       Instant finalDeEjecucion = Instant.now();

	       LOGGER.info("LearningJava - Cerrando recursos ...");
	       String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
	       LOGGER.info("Tiempo de respuesta: ".concat(total));

	       HttpHeaders responseHeaders = new HttpHeaders();
	       responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
	       return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
	   }
	

	  @PreAuthorize("hasRole('GUEST')")
	  @GetMapping("/sayHello")
	  public ResponseEntity<String> sayHelloGuest() {
	     return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
	  }
	  
	//The usage of FeignClient for demo purposes
		 @GetMapping("/getExternalUser/{userId}")
		 public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

				 Post postTest = accountsJSONClient.getPostById(userId);
				 LOGGER.info("Getting post userId..." +postTest.getUserId());
				 LOGGER.info("Getting post body..." +postTest.getBody());
				 LOGGER.info("Getting post title..." +postTest.getTitle());
				 postTest.setUserId("External user "+randomAcountNumber());
				 postTest.setBody("No info in accountBalance since it is an external user");
				 postTest.setTitle("No info in title since it is an external user");
				 LOGGER.info("Setting post userId..." +postTest.getUserId());
				 LOGGER.info("Setting post body..." +postTest.getBody());
				 LOGGER.info("Setting post title...."+postTest.getTitle());
				 HttpHeaders responseHeaders = new HttpHeaders();
				 responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
				 return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
		 }
	}
	
