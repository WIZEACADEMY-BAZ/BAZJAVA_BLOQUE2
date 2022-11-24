package com.Wizeline.maven.learningjavamaven.controller;

import com.Wizeline.maven.learningjavamaven.LearningjavamavenApplication;
import com.Wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.Wizeline.maven.learningjavamaven.model.Post;
import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.Wizeline.maven.learningjavamaven.service.BankAccountService;
import com.Wizeline.maven.learningjavamaven.utils.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.Wizeline.maven.learningjavamaven.utils.Utils.*;

// @Tag(name = "Banking Account" ,
// description = "Contiene operaciones comunes realizadas en las cuentas.")

// @CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/api")
@RestController
public class BankingAccountController {

    @Value("${server.port}")
    private String port;

    private static final String SUCCESS_CODE = "OK000";

    @Autowired
    AccountsJSONClient accountsJSONClient;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    CommonServices commonServices;

    @Autowired
    private KafkaTemplate<Object, Object> template;

    private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @PostMapping(path = "/send/{userId}")
    public void sendUserAccount(@PathVariable Integer userId) {
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO account = accounts.get(userId);
        this.template.send("useraccount-topic", account);
    }

    @GetMapping("/getUserAccount")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
        LOGGER.info(msgProcPeticion);
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
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
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

//    @PutMapping("/putUserAccount")
//    public ResponseEntity<?> putUserAccount(@RequestParam String user, @RequestParam String password, @RequestParam String date) {
//        LOGGER.info(msgProcPeticion);
//        Instant inicioDeEjecucion = Instant.now();
//        ResponseDTO response = new ResponseDTO();
//        HttpHeaders responseHeaders = new HttpHeaders();
//        String responseText = "";
//        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
//        if (isDateFormatValid(date)) {
//            // Valida el password del usuario (password)
//            if (isPasswordValid(password)) {
//                response = commonServices.login(user, password);
//                if (response.getCode().equals(SUCCESS_CODE)) {
//                    BankAccountDTO bankAccountDTO = getAccountDetails(user, date);
//                    Instant finalDeEjecucion = Instant.now();
//                    LOGGER.info("LearningJava - Cerrando recursos ...");
//                    String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//                    LOGGER.info("Tiempo de respuesta: ".concat(total));
//                    return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);
//                }
 //           } else {
//                Instant finalDeEjecucion = Instant.now();
//                LOGGER.info("LearningJava - Cerrando recursos ...");
//                String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//                LOGGER.info("Tiempo de respuesta: ".concat(total));
//                responseText = "Password Incorrecto";
//                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
//            }
//        } else {
//            responseText = "Formato de Fecha Incorrecto";
//        }
//        Instant finalDeEjecucion = Instant.now();
//        LOGGER.info("LearningJava - Cerrando recursos ...");
//       String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
//        LOGGER.info("Tiempo de respuesta: ".concat(total));
//        responseText = "DATO INGRESADO";
//        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
//    }


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

    //The usage of FeignClient for demo purposes
    @GetMapping("/getExternalUser/{userId}")
    public ResponseEntity<Post> getExternalUser(@PathVariable Long userId) {

        Post postTest = accountsJSONClient.getPostById(userId);
        LOGGER.info("Getting post userId..." +postTest.getUserId());
        LOGGER.info("Getting post body..." +postTest.getBody());
        LOGGER.info("Getting post title..." +postTest.getTitle());
        postTest.setUserId("External user "+ randomAccountNumber());
        postTest.setBody("No info in accountBalance since it is an external user");
        postTest.setTitle("No info in title since it is an external user");
        LOGGER.info("Setting post userId..." +postTest.getUserId());
        LOGGER.info("Setting post body..." +postTest.getBody());
        LOGGER.info("Setting post title...."+postTest.getTitle());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(postTest, responseHeaders, HttpStatus.OK);
    }


    //   @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAccountByUser")
    //, produces = MediaType.APPLICATION_JSON_VALUE)
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


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAccountsGroupByType")
    public ResponseEntity<Map<String, List<BankAccountDTO>>> getAccountsGroupByType() {
    // throws JsonProcessingException {

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

//    @PreAuthorize("hasRole('GUEST')")
//    @GetMapping("/sayHello")
//    public ResponseEntity<String> sayHelloGuest() {
//        return new ResponseEntity<>("Hola invitado!!", HttpStatus.OK);
//    }


    @DeleteMapping("/deleteAccounts")
    public ResponseEntity<String> deleteAccounts() {
        bankAccountService.deleteAccounts();
        return new ResponseEntity<>(" All accounts deleted", HttpStatus.OK);
    }

    private BankAccountDTO getAccountDetails(String user, String lastUsage) {
        return bankAccountService.getAccountDetails(user, lastUsage);
    }

//    @GetMapping(value = "/getEncryptedAccounts")
//    public ResponseEntity<List<BankAccountDTO>> getEncryptedAccounts() {
//        LOGGER.info("Se inicia la peticion para getAccountsGroupByType con MongoDB");
//        Instant inicioDeEjecucion = Instant.now();
//        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET de getEncryptedAccounts");
//        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
//        byte[] keyBytes = new byte[]{
//                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef};
//        byte[] ivBytes = new byte[]{
//               0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01};
//        Security.addProvider(new BouncyCastleProvider());
//        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
//        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//        Cipher cipher = null;
//        try {
//            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
//            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//            // Cifraremos solamente el nombre y el country (Se pueden cifrar todos los parámetros que gusten)
 //           for (int i = 0; i < accounts.size(); i++) {
//                String accountName = accounts.get(i).getAccountName();
//                byte[] arrAccountName = accountName.getBytes();
//                byte[] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
 //               int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
   //             ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
     //           accounts.get(i).setAccountName(accountNameCipher.toString());
       //         String accountCountry = accounts.get(i).getCountry();
         //       byte[] arrAccountCountry = accountCountry.getBytes();
           //     byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
//                int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
  //              ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
    //            accounts.get(i).setCountry(accountCountryCipher.toString());
      //      }
      //      } catch(NoSuchAlgorithmException e){
        //        throw new RuntimeException(e);
          //  } catch(NoSuchProviderException e){
            //    throw new RuntimeException(e);
//            } catch(NoSuchPaddingException e){
  //              throw new RuntimeException(e);
    //        } catch(InvalidAlgorithmParameterException e){
      //          throw new RuntimeException(e);
        //    } catch(ShortBufferException e){
          //      throw new RuntimeException(e);
            // } catch(IllegalBlockSizeException e){
//                throw new RuntimeException(e);
  //          } catch(BadPaddingException e){
    //            throw new RuntimeException(e);
      //      } catch(InvalidKeyException e){
        //        throw new RuntimeException(e);
          //  }
//            Instant finalDeEjecucion = Instant.now();
  //          LOGGER.info("LearningJava - Cerrando recursos ...");
    //        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
      //      LOGGER.info("Tiempo de respuesta: ".concat(total));
        //    return new ResponseEntity<>(accounts, HttpStatus.OK);
//        }
}

