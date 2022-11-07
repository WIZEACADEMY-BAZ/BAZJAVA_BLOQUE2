package com.wizeline.maven.learninjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.service.BankAccountService;
import com.wizeline.maven.learninjavamaven.utils.exceptions.ExceptionGenerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class BankingAccountController extends Thread{

    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());

    private static String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @Autowired
    private BankAccountService bankAccountService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user){
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando cursos...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {
        LOGGER.info(msgProcPeticion);
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

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts(){
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("All accounts deleted", HttpStatus.OK);
    }

    @PutMapping("/updateBankAccount/{user}")
    public ResponseEntity<Optional<BankAccountDTO>> updateBankAccount(@PathVariable(value = "user") String user, @Valid
        @RequestBody BankAccountDTO bankAccountDTODetails) throws ExceptionGenerica{

        BankAccountDTO bankAccountDTO = bankAccountService.findByUser(user);
        if(bankAccountDTO != null){
            bankAccountDTO.setUser(bankAccountDTODetails.getUser());
            BankAccountDTO bankAccountDTOUpdate = bankAccountService.save(bankAccountDTO);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

            return new ResponseEntity<>(Optional.ofNullable(bankAccountDTOUpdate), responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/api/createUsersThread")
    public ResponseEntity<String> createUsersThread(){
        String responseTextThread = "";
        StringBuilder text = new StringBuilder();
        try (Scanner scanner = new Scanner(responseTextThread)) {
            while (scanner.hasNext()) {
                text.append(scanner.next());
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            throw new ExceptionGenerica("Fallo al obtener el request del body");
        }
        String textThread = "";
        textThread = text.toString();
		LOGGER.info(textThread);
		//Se inicia el thread
        BankingAccountController thread = new BankingAccountController();
		thread.start();
		//Esperamos a que termine el thread
		while (thread.isAlive());
        return new ResponseEntity<>(responseTextThread, HttpStatus.OK);
    }
}
