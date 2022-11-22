package com.wizeline.entregabledavid.controller;

import com.wizeline.entregabledavid.builder.User;
import com.wizeline.entregabledavid.iterator.Iterator;
import com.wizeline.entregabledavid.iterator.UserDTOCollection;
import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.model.UserDTO;
import com.wizeline.entregabledavid.service.UserService;
import com.wizeline.entregabledavid.utils.CommonServices;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommonServices commonServices;



    private final Bucket bucket;

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
        if (bucket.tryConsume(1)) {
            LOGGER.info(msgProcPeticion);
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
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = new ResponseDTO();

        response = createUser(userDTO.getUser(), userDTO.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info(msgProcPeticion);
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        UserDTOCollection users = userService.populateUsers();
        Iterator baseIterator = users.iterator("ALL");
        while (baseIterator.hasNext()) {
            UserDTO u = baseIterator.next();
            System.out.println(u.toString());
        }
        System.out.println("******");

        // Channel Type Iterator
        Iterator userIterator = users.iterator("user");
        while (userIterator.hasNext()) {
            UserDTO u = userIterator.next();
            System.out.println(u.toString());
        }


        userDTOList.stream().forEach( userDTO -> {
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

    @GetMapping("/getUserBuild")
    public ResponseEntity<Object> getUserBuild(){
        LOGGER.info(msgProcPeticion);

        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        User response = userService.getUserBuild();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);

    }


    public static Map<String, String> splitQuery(URI uri) {
        Map<String, String> queryPairs = new LinkedHashMap<String, String>();
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
        return userService.createUser(user, password);
    }
}
