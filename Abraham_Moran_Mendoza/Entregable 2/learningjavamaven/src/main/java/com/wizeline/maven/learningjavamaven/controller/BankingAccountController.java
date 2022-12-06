package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.AccountHolder;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories.AccountHolderFactory;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories.AztecaFactory;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories.BBVAFactory;
import com.wizeline.maven.learningjavamaven.paterns.creational.abstractfactory.factories.BanorteFactory;
import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;
import static com.wizeline.maven.learningjavamaven.utils.Utils.*;

@RestController
@RequestMapping("/api")
public class BankingAccountController {

  @Value("${server.port}")
  private String port;

  @Autowired
  BankAccountService bankAccountService;

  @Autowired
  AccountsJSONClient accountsJSONClient;

  @Autowired
  private KafkaTemplate<Object, Object> template;

  private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

  @PostMapping(path = "/send/{userId}")
  public void sendUserAccount(@PathVariable Integer userId) {
    List<BankAccountDTO> accounts = bankAccountService.getAccounts();
    BankAccountDTO account = accounts.get(userId);
    this.template.send("useraccount-topic", account);
  }

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

  // Revisión: Implementación de un patrón de diseño de creación
  // Revisión: Escritura en MongoDB
  @PostMapping("/createAccount/{type}")
  public ResponseEntity<BankAccountDTO> createAccount(
      @PathVariable("type")
      Integer type,
      @RequestBody BankAccountDTO request
  ){
    LOGGER.info(INIT_PROCESS);
    Instant inicioDeEjecucion = Instant.now();
    LOGGER.info(PROCESSING_POST_METHOD);

    AccountHolder accountHolder = null;
    AccountHolderFactory accountHolderFactory = null;

    switch(type){
      case 1: accountHolderFactory = new BBVAFactory(); break;
      case 2: accountHolderFactory = new AztecaFactory(); break;
      case 3: accountHolderFactory = new BanorteFactory(); break;
    }

    accountHolder = new AccountHolder(accountHolderFactory);

    return new ResponseEntity<>(accountHolder.getInfo(), HttpStatus.OK);
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

  @GetMapping("/getExternalUser/{userId}")
  public ResponseEntity<PostDTO> getExternalUser(@PathVariable Long userId) {

    PostDTO postTest = accountsJSONClient.getPostById(userId);
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
