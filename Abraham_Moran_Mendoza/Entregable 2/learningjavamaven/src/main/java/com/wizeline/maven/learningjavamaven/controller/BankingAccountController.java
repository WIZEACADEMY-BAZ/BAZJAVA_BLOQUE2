package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@RestController
@RequestMapping("/api")
public class BankingAccountController {

  @Autowired
  BankAccountService bankAccountService;

  private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

  // Revisión: Lectura de MongoDB
  @PreAuthorize("hasRole('USER')")
  @GetMapping("/getAccountByUser")
  public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

    Instant finalDeEjecucion = Instant.now();

    LOGGER.info(CLOSING_RESOURCES);
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
    return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
  }

  // Revisión: Escritura en MongoDB
  @PostMapping("/createAccount")
  public ResponseEntity<BankAccountDTO> createAccount(
      @RequestBody BankAccountDTO request
  ){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_POST_METHOD);

    BankAccountDTO bankAccountDTO = bankAccountService.createAccount(request);

    Instant finalDeEjecucion = Instant.now();

    LOGGER.info(CLOSING_RESOURCES);
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
    return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
  }

  // Revisión: Actualización en MongoDB
  @PutMapping("/{account}/changeCountry")
  public ResponseEntity<BankAccountDTO> changeAccountCountry(
      @PathVariable("account") long accountNumber
      , @RequestParam("country") String country){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);

    Optional<BankAccountDTO> bankAccountDTO = bankAccountService.changeCountry(accountNumber, country);

    Instant finalDeEjecucion = Instant.now();

    LOGGER.info(CLOSING_RESOURCES);
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set(CONTENT_TYPE, CONTENT_TYPE_JSON_CHARSET);
    return new ResponseEntity<>(bankAccountDTO.get(), responseHeaders, HttpStatus.OK);
  }


  // Revisión: Borrado en MongoDB
  @DeleteMapping("/deleteAccounts")
  public ResponseEntity<String> deleteAccounts(){
    bankAccountService.deleteAccounts();
    return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
  }
}
