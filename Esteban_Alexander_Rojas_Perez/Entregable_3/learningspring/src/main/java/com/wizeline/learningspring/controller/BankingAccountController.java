package com.wizeline.learningspring.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.wizeline.learningspring.LearningspringApplication;
import com.wizeline.learningspring.client.AccountsJSONClient;
import com.wizeline.learningspring.model.BankAccountDTO;
import com.wizeline.learningspring.model.Post;
import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.service.BankAccountService;
import com.wizeline.learningspring.utils.CommonServices;
import com.wizeline.learningspring.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import static com.wizeline.learningspring.utils.Utils.randomAcountNumber;

@Tag(name = "Banking Account",
        description = "Contiene operaciones comunes realizadas en las cuentas.")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
public class BankingAccountController {
    @Value("${server.ports}")
    private String port;
    private static final String SUCCESS_CODE = "OK000";
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    CommonServices commonServices;
    @Autowired
    private AccountsJSONClient accountsJSONClient;
    @Autowired
    private KafkaTemplate<Object, Object> template;

    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());
    String msgProcPeticion = "Inicia procesamiento de peticion ...";

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password,
                                            @RequestParam String date) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (Utils.isDateFormatValid(date)) {
            if (Utils.isPasswordValid(password)) {
                response = commonServices.login(user, password);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
                    Instant finalDeEjecucion = Instant.now();
                    LOGGER.info("Cerrando recursos ...");
                    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                            finalDeEjecucion).toMillis()).concat(" segundos."));
                    LOGGER.info("Tiempo de respuesta: ".concat(total));
                    return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
                }
            } else {
                Instant finalDeEjecucion = Instant.now();
                LOGGER.info("Cerrando recursos ...");
                String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                        finalDeEjecucion).toMillis()).concat(" segundos."));
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                responseText = "Password Incorrecto";
                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
            }
        } else {
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountDTO>> getAccounts() {
        LOGGER.info("The port used is "+ port);
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("Procesando peticion HTTP de tipo GET");
        LOGGER.info("Retrieving external endpoint ");

        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

    }

    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountDTO>> getAccountByUser(@RequestParam String user) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccountByUser(user);

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() {

        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();

        LOGGER.info("Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("Cerrando recursos ...");
        String total = String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos.");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity<>(groupedAccounts, HttpStatus.OK);
    }

    @GetMapping("/sayHello")
    public ResponseEntity<String> sayHelloGuest() {
        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
    }

    @DeleteMapping("/deleteAccounts/{userId}")
    public ResponseEntity<String> deleteAccounts() {
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>("Account delete", HttpStatus.OK);
    }

    @PutMapping("/updateAccount/{accountNumber}")
    public ResponseEntity<?> updateAccount(@PathVariable Long accountNumber, @RequestBody BankAccountDTO request) {
        bankAccountService.updateAccount(accountNumber, request);
        return new ResponseEntity<>("Account update", HttpStatus.OK);
    }

    @GetMapping("/getEncryptedAccounts")
    public ResponseEntity<List<BankAccountDTO>> encryptedAccounts(){
        LOGGER.info("The port used is "+ port);
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("Procesando peticion HTTP de tipo GET");
        LOGGER.info("Retrieving external endpoint ");

        List<BankAccountDTO> accounts = bankAccountService.encryptedAccounts();
        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
    }

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

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

}