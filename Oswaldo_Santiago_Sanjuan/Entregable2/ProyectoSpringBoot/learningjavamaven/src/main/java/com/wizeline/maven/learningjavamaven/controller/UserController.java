package com.wizeline.maven.learningjavamaven.controller;

import java.net.URI;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.BankAccountServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;

//import org.bouncycastle.jce.provider.BouncyCastleProvider;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import sun.net.httpserver.ExchangeImpl;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.utils.Utils.isDateFormatValid;

@RestController
@RequestMapping("/api")
public class UserController {
    private static String responseTextThread = "";
    //private ResponseDTO response;
    private static String textThread = "";
    private static String SUCCESS_CODE = "OK000";
    @Autowired
    UserService userService;

    //Usando REST Template
    private final RestTemplate restTemplate;

    //Anoatacion del ejeplo para la sobrecarga de metodos
    @Autowired
    BankAccountService bankAccountService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    @Autowired
    CommonServices commonServices;

    //Creado el contructor REST Template
    @Autowired
    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    //Consulta del login...


    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
        LOGGER.info("Entrando a realizar la peticion ");
        ResponseDTO response = new ResponseDTO();

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        UserDTO userName = new UserDTO();
        StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
        builder.append("?user=" + user);
        builder.append("&password=" + password);
        URI uri = URI.create(builder.toString());
        userName = userName.getParameters(splitQuery(uri));
        response = commonServices.login(userName.getUser(), userName.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request) {
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userService.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    //Este enpoid fue creado para la sobrecarga de metodos del ejemplo que me pide.
    @GetMapping("/getUser")
    public ResponseEntity<?> getUserAccount(@RequestParam String user, @RequestParam String date) {
        LOGGER.info("Metodo procesando la peticion para el ejemplo de la sobrecarga de metodos");
        Instant inicioDeEjecucion = Instant.now();
        ResponseDTO response = new ResponseDTO();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        if (isDateFormatValid(date)) {
            BankAccountDTO bankAccountDTO = getAccountDetails(user); //Metodo resiviendo uer
            Instant finalDeEjecucion = Instant.now();
            LOGGER.info("LearningJava - Cerrando recursos ...");
            String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
            LOGGER.info("Tiempo de respuesta: ".concat(total));
            return new ResponseEntity<>(bankAccountDTO, responseHeaders, HttpStatus.OK);

        } else {
            responseText = "Formato de Fecha Incorrecto";
        }
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }


    //Aplicando la sobrecarga de un  metodo
    private BankAccountDTO getAccountDetails(String user) {
        return bankAccountService.getAccountDetails(user);
    }

    //Aplicando  las expresiones regulares


    @GetMapping("/getEncryptedAccounts")
    public ResponseEntity<List<BankAccountDTO>> getEncryip() {
        LOGGER.info("Prosesando las peticiones del metodo de encritacion.... ");
        //return null;
        //return new ResponseEntity(responseText, responseHeaders, HttpStatus.OK);
        Instant inicioDeEjecucion = Instant.now();
        BankAccountService bankAccountBO = new BankAccountServiceImpl();
        HttpHeaders responseHeaders = new HttpHeaders();
        String responseText = "";
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        //pasa el get
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();

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
        return new ResponseEntity(accounts, responseHeaders, HttpStatus.OK);

    }

//Trabajando con con el metodo creacion de usuario

    //Creacion de usuario
    @PostMapping("/createUsers")
    public ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info("Prepararando para crear los usaurios...... ");
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach(userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
    }

    //Empesando a trabajar en PUT
    //PUT --> Actualizar
    @PutMapping("/UpdateUser")
    //@PostMapping("/createUser")
    public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
        LOGGER.info("Iniciado a actualiza el usuario");
        ResponseDTO response = new ResponseDTO();

        response = createUser(userDTO.getUser(), userDTO.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
        //return new ResponseEntity<>(ResponseDTO, responseHeaders, HttpStatus.OK);
    }
    //Termino de actualizar el usuaro





    private ResponseDTO createUser(String user, String password) {
        return userService.createUser(user, password);
    }

    public void run() {
        try {
            crearUsuarios();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void crearUsuarios() {
        try {
            String user = "user";
            String pass = "password";
            JSONArray jsonArray = new JSONArray(textThread);
            JSONObject userJson;

            ResponseDTO response = null;

            LOGGER.info("jsonArray.length(): " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                userJson = new JSONObject(jsonArray.get(i).toString());
                response = createUser(userJson.getString(user), userJson.getString(pass));
                responseTextThread = new JSONObject(response).toString();
                LOGGER.info("Usuario " + (i + 1) + ": " + responseTextThread);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8), URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

    private void assertTrue(boolean present) {
    }


    private static Optional<Object> getParameterValueObject(Map<String, String> param, String paramName) {
        String val = param.get(paramName);
        if (val != null && val != "") {
            return Optional.ofNullable(val);
        }
        return Optional.ofNullable("NA");
    }

    //Trabajando con mi PAI de REST Template
    @GetMapping("/ResTemplate")
    public Object getApi(){
        String url = "https://pokeapi.co/api/v2/pokemon/ditto";
        Object forObject = restTemplate.getForObject(url, Object.class);
        return forObject;
    }




}
