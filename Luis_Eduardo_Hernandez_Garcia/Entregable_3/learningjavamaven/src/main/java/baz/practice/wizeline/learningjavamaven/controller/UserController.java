package baz.practice.wizeline.learningjavamaven.controller;

import baz.practice.wizeline.learningjavamaven.config.EndpointBean;
import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.model.UserDTO;
import baz.practice.wizeline.learningjavamaven.service.BankAccountBO;
import baz.practice.wizeline.learningjavamaven.service.UserBO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static baz.practice.wizeline.learningjavamaven.utils.Utils.isDateFormatValid;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
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

    private final Bucket bucket;


private static String SUCCESS_CODE = "OK000";

    public static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

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

    //IMPLEMENTANDO DISEÑO THROTTLING
    @GetMapping("/logintThro")
    public ResponseEntity<String> getUsers(@RequestParam String user, String password) {
        if (bucket.tryConsume(1)) {
            LOGGER.info("LearningJava - Procesando Peticion HTTP de tipo GET Login normal THROTTLING - Starting...");
            ResponseDTO response =  new ResponseDTO();
            response = userBO.login(user, password);
            LOGGER.info("Login - Completed");
            return ResponseEntity.ok("It's ok");
        }
        //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    private ResponseDTO createUser(String user, String password) {
        return userBO.createUser(user, password);
    }

}
