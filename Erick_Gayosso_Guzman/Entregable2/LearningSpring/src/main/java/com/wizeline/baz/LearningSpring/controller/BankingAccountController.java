package com.wizeline.baz.LearningSpring.controller;

import com.wizeline.baz.LearningSpring.model.BankAccountDTO;
import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.service.BankAccountService;
import com.wizeline.baz.LearningSpring.utils.CommonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import static com.wizeline.baz.LearningSpring.utils.Utils.isDateFormatValid;
import static com.wizeline.baz.LearningSpring.utils.Utils.isPasswordValid;

@Tag(name = "Banking Account",
        description = "Contiene operaciones comunes realizadas en las cuentas.")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class BankingAccountController {

    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    CommonServices commonServices;

    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            if (isPasswordValid(password)) {
                response = commonServices.login(user, password);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
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
    public ResponseEntity<List<BankAccountDTO>> getAccounts() {
        LOGGER.info(msgProcPeticion);
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAccountByUser")
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
        LOGGER.info(msgProcPeticion);
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
    @GetMapping("/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts() {
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        return new ResponseEntity<>("Hola invitado by Erick!!", HttpStatus.OK);
    }

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }
}