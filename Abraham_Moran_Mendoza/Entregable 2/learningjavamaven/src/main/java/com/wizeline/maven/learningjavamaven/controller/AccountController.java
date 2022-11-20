package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.service.AccountService;
import com.wizeline.maven.learningjavamaven.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;

@RestController
@RequestMapping("/api/exchange")
public class AccountController {

  private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());
  @Autowired
  AccountService accountService;

  @GetMapping("/getUserAccount")
  public ResponseEntity<BankAccountDTO>  getAccount(
      @RequestParam("user")
      String user,
      @RequestParam("password")
      String password,
      @RequestParam("date")
      String date ){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    BankAccountDTO responseBody = accountService.getAccount(user,password,date);
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/getAccounts")
  public ResponseEntity<List<BankAccountDTO>> getAccounts(){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> responseBody = accountService.getAccounts();
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/getAccountByName")
  public ResponseEntity<List<BankAccountDTO>> getAccountByName(
      @RequestParam("name")
      String name ){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> responseBody = accountService.getAccountByName(name);
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/getAccountsByUser")
  public ResponseEntity<List<BankAccountDTO>> getAccountByUser(
      @RequestParam("user")
      String userString ){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> responseBody = accountService.getAccountByUser(userString);
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/getAccountsGroupByType")
  public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType(){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    Map<String, List<BankAccountDTO>> responseBody = accountService.getAccountsGroupByType();
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @GetMapping("/getEncryptedAccounts")
  public ResponseEntity<List<BankAccountDTO>> getEncryptedAccounts(){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> responseBody = accountService.getEncryptedAccounts();
    LOGGER.info(CLOSING_RESOURCES);
    Instant finalDeEjecucion = Instant.now();
    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(SECONDS_MESSAGE));
    LOGGER.info(TIME_OF_RESPONSE.concat(total));
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
