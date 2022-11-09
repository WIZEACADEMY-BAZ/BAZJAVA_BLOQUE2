package com.wizeline.gradle.learningjavagradle.controller;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;

@RestController
@RequestMapping("/user")
public class UserController{

	@Autowired
	UserService userService;

	@Autowired
	CommonServices commonServices;

	@Autowired
	BankAccountService bankAccountService;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

	@GetMapping("/login")
	public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
		LOGGER.info(msgProcPeticion);
		ResponseDTO response = new ResponseDTO();

		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		UserDTO userName = new UserDTO();
		StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
		builder.append("?user=" + user);
		builder.append("&password=" + password);
		URI uri = URI.create(builder.toString());
		userName = userName.getParameters(splitQuery(uri));
		response = commonServices.login(userName.getUser(), userName.getPassword());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
	}

	@PostMapping("createUser")
	public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
		LOGGER.info(msgProcPeticion);
		ResponseDTO response = new ResponseDTO();
		
		
		response = createUser(userDTO.getUser(), userDTO.getPassword());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return ResponseEntity.ok(response);
	}

	@GetMapping("getAccountByUser")
	public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user){
		LOGGER.info(msgProcPeticion);
		Instant inicioDeEjecucion = Instant.now();
		LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
		List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

		Instant finalDeEjecucion = Instant.now();

		LOGGER.info("LearningJava - Cerrando recursos ...");
		String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos"));
		LOGGER.info("Tiempo de respuesta: ".concat(total));

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
	}
	
	@PutMapping("updateUser")
	public ResponseDTO updateUser(@RequestBody UserDTO userDTO) {
		return userService.updateUser(userDTO.getUser(), userDTO.getPassword());
	}
	
	@DeleteMapping("deleteUser")
	public ResponseDTO deleteUser(@RequestParam String user)
	{
		return userService.deleteUser(user); 
	}
	
	public static Map<String, String> splitQuery(URI uri) {
		Map<String, String> queryPairs = new LinkedHashMap<String, String>();
		String query = uri.getQuery();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
					URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
		}
		return queryPairs;
	}

	private ResponseDTO createUser(String user, String password) {
		if(password != null) {
			return userService.createUser(user, password);
		} else {
			return userService.createUser(user);
		}
		
	}

}
