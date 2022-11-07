package com.wizeline.learningspring.controller;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import com.wizeline.learningspring.LearningspringApplication;
import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.model.UserDTO;
import com.wizeline.learningspring.service.UserService;
import com.wizeline.learningspring.utils.CommonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User",
        description = "Contiene operaciones comunes realizadas para usuarios.")
@RequestMapping("/api")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CommonServices commonServices;
    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());
    String msgProcPeticion = "Inicia procesamiento de peticion ...";

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestParam String user, @RequestParam String password){
        LOGGER.info(msgProcPeticion);

        LOGGER.info("Procesando peticion HTTP de tipo GET");
        UserDTO userName = new UserDTO();
        StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
        builder.append("?user=" + user);
        builder.append("&password=" + password);
        URI uri = URI.create(builder.toString());
        userName = userName.getParameters(splitQuery(uri));
        ResponseDTO response = commonServices.login(userName.getUser(), userName.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public  ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
        LOGGER.info(msgProcPeticion);
        ResponseDTO response = createUser(userDTO.getUser(), userDTO.getPassword());

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
        LOGGER.info(msgProcPeticion);
        AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
        List<ResponseDTO> responseList = new ArrayList<>();

        userDTOList.stream().forEach( userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<>(responseList, responseHeaders, HttpStatus.OK);
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
