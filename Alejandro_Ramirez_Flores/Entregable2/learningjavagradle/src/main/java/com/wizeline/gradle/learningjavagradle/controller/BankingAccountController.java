package com.wizeline.gradle.learningjavagradle.controller;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.isDateFormatValid;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.isPasswordValid;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

@RestController
@RequestMapping("/api")
public class BankingAccountController {

	private static final String SUCCESS_CODE = "OK000";

	@Autowired
	UserService userService;

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	CommonServices commonServices;

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

	private BankAccountDTO getAccountDetails(String user, String lastUsage) {
		return bankAccountService.getAccountDetails(user, lastUsage);
	}

	@DeleteMapping("/deleteAccounts")
	public ResponseEntity<String> deleteAccounts() {
		bankAccountService.deleteAccounts();
		return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
	}
	
	@GetMapping("/getAccountByUser")
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
}
