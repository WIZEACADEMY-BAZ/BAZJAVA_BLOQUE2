package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.OutputStream;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
public class UserController extends Thread {

    @Autowired
    UserService userService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    RestTemplate restTemplate;

    private static final Logger LOGGER = Logger.getLogger((UserController.class.getName()));

    private static String responseTextThread = "";
    private ResponseDTO response;
    private static String textThread = "";

    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password) {
        LOGGER.info(("LearningJava - Procesando peticion HTTP de tipo GET - Starting..."));
        ResponseDTO response = new ResponseDTO();
        response = userService.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request) {
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/putUser")
    public ResponseEntity<ResponseDTO> UpdateUser(@RequestBody UserDTO request) {
        System.out.println("Entrando al put ");
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> deleteUser(@RequestBody UserDTO request) {
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
    // Aqui se implementa el Cifrado
    @GetMapping(value = "/getEncryptedAccounts", produces = "application/json")
    public ResponseEntity<ResponseDTO> getEncryptedAccounts() {
        LOGGER.info("Procesando las peticiones cifradas");
        ResponseDTO response = new ResponseDTO();
        Instant inicioDeEjecucion = Instant.now();
        BankAccountServiceImpl bankAccountBO = new BankAccountServiceImpl();
        String responseText = "";
        /** Validates the type of http request  */
            List<BankAccountDTO> accounts = bankAccountBO.getAccounts();

            // Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
            byte[] keyBytes = new byte[]{
                    0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
            };
            byte[] ivBytes = new byte[]{
                    0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
            };
            Security.addProvider(new BouncyCastleProvider());
            SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
                cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
                // Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)
                for (int i = 0; i < accounts.size(); i++) {
                    String accountName = accounts.get(i).getAccountName();
                    byte[] arrAccountName = accountName.getBytes();
                    byte[] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
                    int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
                    ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
                    accounts.get(i).setAccountName(accountNameCipher.toString());

                    String accountCountry = accounts.get(i).getCountry();
                    byte[] arrAccountCountry = accountCountry.getBytes();
                    byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
                    int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
                    ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
                    accounts.get(i).setCountry(accountCountryCipher.toString());

                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchProviderException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            } catch (ShortBufferException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
       // }
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/optional")
    public ResponseEntity<BankAccountDTO> AccountNumber(@RequestParam Long accountNumber) {
        LOGGER.info("Iniciado la ejecuccion... ");
        Optional<BankAccountDTO> opt = pruebas(accountNumber);
        if (opt.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(opt.get(), responseHeaders, HttpStatus.OK);
    }

    public Optional<BankAccountDTO> pruebas(long accountNumber){
        Query query = new Query();
        query.addCriteria(Criteria.where("accountNumber").is(accountNumber));
        BankAccountDTO result = mongoTemplate.findOne(query, BankAccountDTO.class);
        Optional<BankAccountDTO> opt =  Optional.ofNullable(result);
        return opt;
    }

    // Update en mongo
    public BankAccountDTO findUserByName(long accountName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("accountName").is(accountName));
        Update update = new Update();
        update.set("accountName", accountName);
        BankAccountDTO bankAccountDto = (BankAccountDTO) mongoTemplate.update(BankAccountDTO.class);
        return bankAccountDto;
    }

    public void crearUser(String user, String pass) {

    }
    //Aqui se implementa concurrencia
    public void run(){
         String user = "user";
         String pass = "password";
         JSONArray jsonArray = new JSONArray(textThread);
         JSONObject user1 = new JSONObject(jsonArray.get(0).toString());
         JSONObject user2 = new JSONObject(jsonArray.get(1).toString());
         JSONObject user3 = new JSONObject(jsonArray.get(2).toString());
         // Creamos usuario 1
         crearUser(user1.getString(user), user1.getString(pass));
         responseTextThread = new JSONObject(response).toString();
         LOGGER.info("Usuario 1: " + responseTextThread);
         try {
             Thread.sleep(1000);
         } catch (InterruptedException ex) {
             throw new RuntimeException(ex);
         }
         // Creamos usuario 2
         crearUser(user2.getString(user), user2.getString(pass));
         responseTextThread = new JSONObject(response).toString();
         LOGGER.info("Usuario 2: " + responseTextThread);
         try {
             Thread.sleep(1000);
         } catch (InterruptedException ex) {
             throw new RuntimeException(ex);
         }
         // Creamos usuario 3
         crearUser(user3.getString(user), user3.getString(pass));
         responseTextThread = new JSONObject(response).toString();
         LOGGER.info("Usuario 3: " + responseTextThread);

    }

    @GetMapping("/ResTemplate")
    public Object getApi(){
        String url = "https://pokeapi.co/api/v2/pokemon/ditto";
        Object forObject = restTemplate.getForObject(url, Object.class);
        return forObject;
    }

}