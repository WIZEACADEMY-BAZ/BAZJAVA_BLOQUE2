package com.wizeline.entregabledavid.controller;

import com.wizeline.entregabledavid.model.BankAccountDTO;
import com.wizeline.entregabledavid.service.BankAccountService;
import com.wizeline.entregabledavid.utils.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.entregabledavid.utils.Utils.*;

@RequestMapping("/api")
@RestController
public class BankingAccountController {
    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    CommonServices commonServices;

    @Autowired
    private KafkaTemplate<Object, Object> template;


    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";
    String msgGetPeticion = "LearningJava - Procesando peticion HTTP de tipo GET";
    String msgClosePeticion = "LearningJava - Cerrando recursos ...";

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts() {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info(msgGetPeticion);
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info(msgClosePeticion);
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

    }


    @GetMapping(value = "/getAccountsByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountsByUser(@RequestParam String user) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info(msgGetPeticion);
        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info(msgClosePeticion);
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        LOGGER.info(String.valueOf(new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK)));
        return  new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
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


    @PutMapping(value = "/putAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> putAccountByUser(@RequestParam String user) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        LOGGER.info(msgGetPeticion);
        BankAccountDTO account = bankAccountService.putAccountByUser(user);
        if (account == null){
            return  new ResponseEntity<>("No existe el usuario", responseHeaders, HttpStatus.OK);
        }
        String balanceCifrado = cifrarBalance(account.getAccountBalance());
        account.setAccountBalanceCifrado(balanceCifrado);
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info(msgClosePeticion);
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        LOGGER.info(String.valueOf(new ResponseEntity<>(account, responseHeaders, HttpStatus.OK)));
        return  new ResponseEntity<>(account, responseHeaders, HttpStatus.OK);
    }

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }
}
