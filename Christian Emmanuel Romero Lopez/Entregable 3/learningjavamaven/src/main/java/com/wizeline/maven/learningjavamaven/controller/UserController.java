package com.wizeline.maven.learningjavamaven.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.maven.learningjavamaven.elements.Element;
import com.wizeline.maven.learningjavamaven.elements.JsonElement;
import com.wizeline.maven.learningjavamaven.elements.XmlElement;
import com.wizeline.maven.learningjavamaven.patterns.ClientModel;
import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import com.wizeline.maven.learningjavamaven.model.UserModel;
import com.wizeline.maven.learningjavamaven.patterns.ElementVisitor;
import com.wizeline.maven.learningjavamaven.patterns.Invoker;
import com.wizeline.maven.learningjavamaven.patterns.Receiver;
import com.wizeline.maven.learningjavamaven.repository.CopyCommand;
import com.wizeline.maven.learningjavamaven.repository.Visitor;
import com.wizeline.maven.learningjavamaven.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.utils.Utils.cifrarDato;
import static com.wizeline.maven.learningjavamaven.utils.Utils.isPasswordValid;
import static com.wizeline.maven.learningjavamaven.utils.Utils.generateUuid;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    private final Bucket bucket;

    List<Element> elements = new ArrayList<>();

    public UserController() {
        Refill refill = Refill.intervally(5, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(5, refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private static String msgProcPeticion = "LearningJavaMaven - Inicia procesamiento de peticion ...";
    private static String path = "http://localhost:8081/api";
    private Instant inicio;

    @GetMapping(value="/login", produces="application/json")
    public ResponseEntity<ResponseModel> login(@RequestParam String user, @RequestParam String password){
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();
        ResponseModel response;
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        LOGGER.info("LearningJavaMaven - Procesando peticion HTTP de tipo GET");
        UserModel userModel = new UserModel();
        StringBuilder stringBuilder = new StringBuilder(path + "/login");
        stringBuilder.append("?user=" + user);
        stringBuilder.append("&password=" + password);
        URI uri = URI.create(stringBuilder.toString());

        userModel = userModel.getParameters(splitQuery(uri));


        response = userService.login(userModel.getUser(), cifrarDato(userModel.getPassword()));

        LOGGER.info("Login - Completed");
        return getResponseModelResponseEntity(response, inicio);
    }

    @PostMapping(value="/createUser", produces="application/json")
    public ResponseEntity<?> createUser(@RequestBody UserModel request) throws JsonProcessingException {

        if (bucket.tryConsume(1)) {
            LOGGER.info(msgProcPeticion);
            inicio = Instant.now();
            ResponseModel response;
            HttpHeaders responseHeaders = new HttpHeaders();
            String responseText = "";
            responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

            if (isPasswordValid(request.getPassword())) {
                response = userService.createUser(request.getUser(),request.getPassword());
                String total = String.valueOf(Duration.between(inicio, Instant.now()).toMillis()).concat(" segundos.");
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
            } else {
                LOGGER.info("LearningJava - Cerrando recursos ...");
                String total = String.valueOf(Duration.between(inicio, Instant.now()).toMillis()).concat(" segundos.");
                LOGGER.info("Tiempo de respuesta: ".concat(total));
                responseText = "Password Incorrecto";
                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
            }

        }

        //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping("/createUsers")
    public  ResponseEntity<List<ResponseModel>> createUsersAccount(@RequestBody List<UserModel> userDTOList) {
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();
        AtomicReference<ResponseModel> response = new AtomicReference<>(new ResponseModel());
        List<ResponseModel> responseList = new ArrayList<>();

        userDTOList.stream().forEach( userDTO -> {
                    String user = userDTO.getUser();
                    String password = userDTO.getPassword();
                    response.set(createUser(user, password));
                    responseList.add(response.get());
                }
        );

        LOGGER.info("Create Users - Completed");
        return getResponseModelResponseEntity(responseList, inicio);
    }

    @PreAuthorize("hasRole('GUEST')")
    @GetMapping("/sayBye")
    public ResponseEntity<String> sayByeGuest() {

        elements.add(new XmlElement(generateUuid()));
        elements.add(new JsonElement(generateUuid()));

        Visitor visitor = new ElementVisitor();
        visitor.visit(elements);

        return new ResponseEntity<>("Adios invitado!!", HttpStatus.OK);
    }

    @GetMapping("/createClient")
    public ResponseEntity<String> createClientAccount(@RequestParam String apellido, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam String telefono, @RequestParam String tipo) {
        LOGGER.info(msgProcPeticion);
        inicio = Instant.now();

        ClientModel cliente = new ClientModel.ClientBuilder()
                .apellidoPaterno(apellido)
                .nombre(nombre)
                .edad(edad)
                .telefono(telefono)
                .tipoCliente(tipo)
                .build();

        elements.add(new XmlElement(generateUuid() + " - " + cliente));
        elements.add(new JsonElement(generateUuid() + " - " + cliente));

        Visitor visitor = new ElementVisitor();
        visitor.visit(elements);

        LOGGER.info( "Msg:" + cliente );
        LOGGER.info("Create Client - Completed");
        String total = String.valueOf(Duration.between(inicio, Instant.now()).toMillis()).concat(" milisegundos");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        return new ResponseEntity<>("Se ejecuto el cliente!!", HttpStatus.OK);
    }



    @NotNull
    private ResponseEntity<ResponseModel> getResponseModelResponseEntity(ResponseModel responseModel, Instant inicioDeEjecucion) {

        String total = String.valueOf(Duration.between(inicioDeEjecucion, Instant.now()).toMillis()).concat(" milisegundos");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

        return new ResponseEntity<>(responseModel, responseHeaders, HttpStatus.OK);
    }

    @NotNull
    private ResponseEntity<List<ResponseModel>> getResponseModelResponseEntity(List<ResponseModel> responseList, Instant inicioDeEjecucion) {

        String total = String.valueOf(Duration.between(inicioDeEjecucion, Instant.now()).toMillis()).concat(" milisegundos");
        LOGGER.info("Tiempo de respuesta: ".concat(total));

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

    private ResponseModel createUser(String user, String password) {
        return userService.createUser(user, password);
    }
}
