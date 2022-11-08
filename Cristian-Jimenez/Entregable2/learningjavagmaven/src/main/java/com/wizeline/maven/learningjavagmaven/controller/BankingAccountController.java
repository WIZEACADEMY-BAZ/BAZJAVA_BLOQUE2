package com.wizeline.maven.learningjavagmaven.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import com.wizeline.maven.learningjavagmaven.LearningjavagmavenApplication;
import com.wizeline.maven.learningjavagmaven.service.BankAccountService;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.utils.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.wizeline.maven.learningjavagmaven.utils.Utils.isDateFormatValid;
import static com.wizeline.maven.learningjavagmaven.utils.Utils.isPasswordValid;


@RequestMapping("/api")
@RestController
public class BankingAccountController {

    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    private BankAccountService bankAccountService;


    @Autowired
    CommonServices commonServices;

    private static final Logger LOGGER = Logger.getLogger(LearningjavagmavenApplication.class.getName());



    @PreAuthorize("hasRole('USER')")

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        LOGGER.info("inicia Peticion getuserAccount");
        Instant inicioDeEjecucion = Instant.now();
        ResponseModel response = new ResponseModel();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            // Valida el password del usuario (password)
            if (isPasswordValid(password)) {
                response = commonServices.login(user, password);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    BankAccountModel bankAccountDTO = getAccountDetails(user, date);
                    Instant finalDeEjecucion = Instant.now();
                    LOGGER.info("LearningJava - Cerrando recursos ...");
                    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
                    LOGGER.info("Tiempo de respuesta: ".concat(total));
                    return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
                }
            } else {
                Instant finalDeEjecucion = Instant.now();
                LOGGER.info("LearningJava - Cerrando recursos ...");
                String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                responseText = "Password Incorrecto";
                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
            }
        } else {
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountModel>> getAccounts() {
        LOGGER.info("inicializando peticion");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountModel> accounts = bankAccountService.getAccounts();

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

    }

    @GetMapping("/getAccountByUser")
    public ResponseEntity<List<BankAccountModel>> getAccountByUser(@RequestParam String user) {
        LOGGER.info("inicializando peticion");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountModel> accounts = bankAccountService.getAccountByUser(user);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountModel>>> getAccountsGroupByType() {

        LOGGER.info("iniciando peticion");
        Instant inicioDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountModel> accounts = bankAccountService.getAccounts();

        // Aqui implementaremos la programación funcional
        Map<String, List<BankAccountModel>> groupedAccounts;
        Function<BankAccountModel, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
    }


    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts() {
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
    }

    private BankAccountModel getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

}