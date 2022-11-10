package baz.practice.wizeline.learningjavamaven.controller;

import baz.practice.wizeline.learningjavamaven.config.EndpointBean;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.model.UserDTO;
import baz.practice.wizeline.learningjavamaven.service.BankAccountBO;
import baz.practice.wizeline.learningjavamaven.service.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static baz.practice.wizeline.learningjavamaven.utils.Utils.isDateFormatValid;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserBO userBO;


private static String SUCCESS_CODE = "OK000";

    public static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    //Para Login Sencillo
    @GetMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
        LOGGER.info("LearningJava - Procesando Peticion HTTP de tipo GET Login normal - Starting...");
        ResponseDTO response =  new ResponseDTO();
        response = userBO.login(user, password);
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
    //Para crear usuarios normalmente
    @PostMapping(value = "/createUser", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request){
        LOGGER.info("LearningJava - Procesando Peticion HTTP de tipo POST crear usuario normal - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userBO.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - Compleated");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info("LearningJava - Procesando Peticion HTTP de tipo POST crear usuario normal - Starting...");
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

    @PutMapping(value = "/createUserUnic", produces = "application/json")
    public ResponseEntity<ResponseDTO> createUserUnic(@RequestBody UserDTO request){
        LOGGER.info("LearningJava - Procesando Peticion HTTP de tipo PUT crear usuario normal - Starting...");
        ResponseDTO response = new ResponseDTO();
        response = userBO.createUser(request.getUser(), request.getPassword());
        LOGGER.info("Create user - Compleated");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String query = uri.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8));
        }
        return queryPairs;
    }

    private ResponseDTO createUser(String user, String password) {
        return userBO.createUser(user, password);
    }

}
