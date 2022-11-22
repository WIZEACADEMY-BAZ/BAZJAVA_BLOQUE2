package com.wizeline.learningjavamaven.controller;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.model.UserDateDTO;
import com.wizeline.learningjavamaven.service.UserService;
import com.wizeline.learningjavamaven.utils.CommonServices;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@Tag(name = "User",
    description = "Contiene operaciones comunes realizadas para usuarios.")
@RequestMapping("/api")
@RestController
public class UserController {

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  String msgProcPeticion = "LearningJava - Inicia procesamiento de peticion ...";

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

  @GetMapping("/users")
  public ResponseEntity<String> getUsers() {
    if (bucket.tryConsume(1)) {
      return ResponseEntity.ok("It's ok");
    }

    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/login")
  public ResponseEntity<ResponseDTO> loginUser(@RequestParam String username, @RequestParam String password) {
    LOGGER.info(msgProcPeticion);
    LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
    UserDTO userName = new UserDTO();
    StringBuilder builder = new StringBuilder("http://localhost:8080/api/login");
    builder.append("?username=" + username);
    builder.append("&password=" + password);
    URI uri = URI.create(builder.toString());
    userName = userName.getParameters(splitQuery(uri));
    ResponseDTO response = commonServices.login(userName.getUsername(), userName.getPassword());

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<ResponseDTO>(response, responseHeaders, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/createUser")
  public ResponseEntity<ResponseDTO> createUserAccount(@RequestBody UserDTO userDTO) {
    LOGGER.info(msgProcPeticion);

    ResponseDTO response = createUser(userDTO.getUsername(), userDTO.getPassword());

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/createUsers")
  public ResponseEntity<List<ResponseDTO>> createUsersAccount(@RequestBody List<UserDTO> userDTOList) {
    LOGGER.info(msgProcPeticion);
    AtomicReference<ResponseDTO> response = new AtomicReference<>(new ResponseDTO());
    List<ResponseDTO> responseList = new ArrayList<>();

    userDTOList.stream().forEach(userDTO -> {
      String user = userDTO.getUsername();
      String password = userDTO.getPassword();
      response.set(createUser(user, password));
      responseList.add(response.get());
    });

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");

    return new ResponseEntity<List<ResponseDTO>>(responseList, responseHeaders, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/updateUser")
  public ResponseEntity<ResponseDTO> updateUserAccount(@RequestBody UserDTO userDTO) {
    LOGGER.info(msgProcPeticion);

    ResponseDTO response = userService.updateUser(userDTO.getUsername(), userDTO.getPassword());

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/deleteUser")
  public ResponseEntity<ResponseDTO> deleteUserAccount(@RequestParam String username) {
    LOGGER.info(msgProcPeticion);

    ResponseDTO response = userService.deleteUser(username);

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

  private ResponseDTO createUser(String username, String password) {
    return userService.createUser(username, password);
  }

  @GetMapping("/userDate")
  public ResponseEntity<List<UserDateDTO>> userDate(@RequestParam String userId) {
    LOGGER.info(msgProcPeticion);

    List<UserDateDTO> response = userService.getUserDateIterator(userId);

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
    return new ResponseEntity<>(response, responseHeaders, HttpStatus.OK);
  }
}
