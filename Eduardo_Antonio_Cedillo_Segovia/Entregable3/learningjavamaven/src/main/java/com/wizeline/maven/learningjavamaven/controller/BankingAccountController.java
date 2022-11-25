package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.LearningjavamavenApplication;
import com.wizeline.maven.learningjavamaven.chainofresponsibility.*;
import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.*;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.*;
import static com.wizeline.maven.learningjavamaven.utils.Utils.*;


@Tag(name="Banking Account", description = "Contiene operaciones comunes realizadas en las cuentas")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api")
@RestController
    public class BankingAccountController {

    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    private KafkaTemplate<Object, Object> template;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    CommonServices commonServices;

    @Autowired
    AccountsJSONClient accountsJSONClient;

        private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
        String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @Value("${server.port}")
    private String port;

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            // Valida el password del usuario (password)
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
            LOGGER.info("The port used is "+ port);
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



    //Voy a tratar de implementar  el patron de diseño Factory
    @GetMapping("/factory")
    public ResponseEntity<List<Account>> Factory(){
        LOGGER.info("Entrando mi patron factory ");
        AccountFactory obj = new AccountFactory();

        Account cuenta = obj.getcuenta(NOMINA);
        cuenta.setAccountNumber(34443);
        cuenta.setEfectivo(8);
        cuenta.tieneFondos();

        Account cuenta2 = obj.getcuenta(PLATINUM);
        cuenta2.setAccountNumber(56456);
        cuenta2.setEfectivo(3);
        cuenta2.tieneFondos();

        Account cuenta3 = obj.getcuenta(AHORRO);
        cuenta3.setAccountNumber(546);
        cuenta3.setEfectivo(0);
        cuenta3.tieneFondos();


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(List.of(cuenta,cuenta2,cuenta3),responseHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(value = "/getAccountByUser", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping(value = "/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() throws JsonProcessingException {

        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

        // Aqui implementaremos la programación funcional
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        //Stream de datos
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

        private BankAccountDTO getAccountDetails(String user, String lastUsage) {
            return bankAccountService.getAccountDetails(user);
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

    @GetMapping("/responsibility")
    public ResponseEntity <List<MoneyChainHandler>> responsibility(@RequestParam Integer cantidad){
        LOGGER.info("Entrando ");
        MoneyChainHandler hundredDollarHandler_100 = new HundrenDollarHandler_100(100);
        MoneyChainHandler fiftyDollarHandler_50 = new FiftyDollarHandler_50(50);
        MoneyChainHandler tenDollarHandler_10 = new TenDollarHandler_10(10);
        MoneyChainHandler fiveDollarHandler_5 = new FiveDollarHandler_5(5);
        MoneyChainHandler twoDollarHandler_2 = new TwoDollarHandler_2(2);
        MoneyChainHandler oneDollarHandler_1 = new OneDollarHandler_1(1);

        // Setting up the change

        hundredDollarHandler_100.setNextHandler(fiftyDollarHandler_50);
        fiftyDollarHandler_50.setNextHandler(tenDollarHandler_10);
        tenDollarHandler_10.setNextHandler(fiveDollarHandler_5);
        fiveDollarHandler_5.setNextHandler(twoDollarHandler_2);
        twoDollarHandler_2.setNextHandler(oneDollarHandler_1);

        System.out.println("Please enter amount you want to withdraw ex: 3450 ");
        //int choice = scan.nextInt();
        hundredDollarHandler_100.handler(cantidad);

        System.out.println("=============================");


        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(responseHeaders, HttpStatus.OK);
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

