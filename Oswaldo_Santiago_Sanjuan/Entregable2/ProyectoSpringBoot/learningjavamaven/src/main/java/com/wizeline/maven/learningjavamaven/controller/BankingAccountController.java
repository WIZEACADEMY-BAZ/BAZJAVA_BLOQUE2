package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.Post;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.utils.Utils.randomAcountNumber;
//@Value("${server.port}")
//private String port;

@RequestMapping("/api")
@RestController

public class BankingAccountController {
    private static String SUCCESS_CODE = "OK000";
    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

    @Autowired
    AccountsJSONClient accountsJSONClient;

    @Autowired
    private BankAccountService bankAccountService;


    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts() {
        //LOGGER.info("The port used is "+ port);
        LOGGER.info("Iniciando ");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
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



    //Definos los enpoins
    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
        LOGGER.info("msgProcPeticion");
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
        // Implementación
        return null;
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

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }


    //?accountNumber=xxxxx
//@RequestParam String user

    //Consultar todas las cuentas y buscarla por nombre utilizando Optional por si no es encontrada
    @GetMapping("/getAccountByAccountNumber")
    public ResponseEntity<BankAccountDTO> getAccountByAccountNumber(@RequestParam long accountNumber) {
        LOGGER.info("Iniciado la ejecuccion... ");
        Optional<BankAccountDTO> optional = bankAccountService.getAccountByAccountNumber(accountNumber);
        if (optional.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(optional.get(), responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/putCountry")
    public ResponseEntity<BankAccountDTO> putCountry(@RequestParam String country){
        LOGGER.info("Iniciando la ejecucion . . . ");
        BankAccountDTO modificar = bankAccountService.putCountry(country);
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(responseHeaders, HttpStatus.OK);
    }

}
