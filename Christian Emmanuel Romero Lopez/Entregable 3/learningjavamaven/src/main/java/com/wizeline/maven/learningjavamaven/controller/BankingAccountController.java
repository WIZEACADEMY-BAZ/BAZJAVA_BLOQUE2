package com.wizeline.maven.learningjavamaven.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import com.wizeline.maven.learningjavamaven.model.Post;
import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.wizeline.maven.learningjavamaven.utils.Utils.*;

@RequestMapping("/api")
@RestController
public class BankingAccountController {

    private final Bucket bucket;

    public BankingAccountController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private static final String SUCCESS_CODE = "OK000";

    @Value("${server.port}")
    private String port;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    CommonServices commonServices;

    @Autowired
    AccountsJSONClient accountsJSONClient;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    private static final Logger LOGGER = Logger.getLogger(BankingAccountController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        if (bucket.tryConsume(1)) {
            LOGGER.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            ResponseModel response;
            HttpHeaders responseHeaders = new HttpHeaders();
            String responseText = "";
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
            if (isDateFormatValid(date)) {

                if (isPasswordValid(password)) {
                    response = commonServices.login(user, password);
                    if (response.getCode().equals(SUCCESS_CODE)) {
                        BankAccountModel bankAccountModel = getAccountDetails(user, date);
                        Instant finalDeEjecucion = Instant.now();
                        LOGGER.info("LearningJava - Cerrando recursos ...");
                        String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
                        LOGGER.info("Tiempo de respuesta: ".concat(total));
                        return new ResponseEntity<>(bankAccountModel, responseHeaders, HttpStatus.OK);
                    }
                } else {
                    Instant finalDeEjecucion = Instant.now();
                    LOGGER.info("LearningJava - Cerrando recursos ...");
                    String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
                    LOGGER.info("Tiempo de respuesta: ".concat(total));
                    responseText = "Password Incorrecto";
                    return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
                }
            } else {
                responseText = "Formato de Fecha Incorrecto";
            }
            Instant finalDeEjecucion = Instant.now();
            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<BankAccountModel>> getAccounts() throws JsonProcessingException {
        if (bucket.tryConsume(1)) {
            LOGGER.info("The port used is "+ port);
            LOGGER.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
            List<BankAccountModel> accounts = bankAccountService.getAccounts();

            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
            LOGGER.info("Tiempo de respuesta: ".concat(total));

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
            return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BankAccountModel>> getAccountByUser(@RequestParam String user) {
        if (bucket.tryConsume(1)) {
            LOGGER.info(msgProcPeticion);
            Instant inicioDeEjecucion = Instant.now();
            LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
            List<BankAccountModel> accounts = bankAccountService.getAccountByUser(user);

            Instant finalDeEjecucion = Instant.now();

            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
            LOGGER.info("Tiempo de respuesta: ".concat(total));

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
            return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountModel>>> getAccountsGroupByType() throws JsonProcessingException {

        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountModel> accounts = bankAccountService.getAccounts();

        // Aqui implementaremos la programación funcional
        Map<String, List<BankAccountModel>> groupedAccounts;
        Function<BankAccountModel, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos.");
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
        ArrayList<String> mensaje = new ArrayList<String>() {{
            add("Hola");
            add(" ");
            add("invitado");
            add("!");
            add("!");
        }};

        muestraMensajeConsola(mensaje);
        return new ResponseEntity<>("Hola invitado!! "+mensaje, HttpStatus.OK);
    }

    private BankAccountModel getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
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

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountModel> accounts = bankAccountService.getAccounts();
        BankAccountModel account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }

}