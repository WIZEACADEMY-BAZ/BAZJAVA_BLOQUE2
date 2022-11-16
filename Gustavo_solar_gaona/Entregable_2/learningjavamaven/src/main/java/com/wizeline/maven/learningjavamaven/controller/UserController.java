package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

// Revisión: Uso de al menos tres anotaciones
@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserService userService;

  private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

  @GetMapping(value = "/login", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
    LOGGER.info( PROCESSING_GET_METHOD );
    ResponseDTO response = new ResponseDTO();
    //response = userService.login(user, password);
    //El método login es reemplazado por userMongo el cual aplica la conexión
    response = userService.getUserMongo(user, password);
    LOGGER.info("Login - Completed");
    return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
  @GetMapping(value = "/users/{userId}/todos", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<TodoDTO>> getGetUserTodos(@PathVariable("userId") String userId){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<TodoDTO> todoDTOS = userService.getUserTodos(userId);
    return new ResponseEntity<List<TodoDTO>>(todoDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
  @GetMapping(value = "/users/{userId}/posts", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<PostDTO>> getGetUserPosts(@PathVariable("userId") String userId){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<PostDTO> postDTOS = userService.getUserPosts(userId);
    return new ResponseEntity<List<PostDTO>>(postDTOS, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping(value = "/users/{userId}/filteredTodos", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<TodoDTO>> getGetUserTodos(@PathVariable("userId") String userId, @RequestParam("status") boolean status ){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<TodoDTO> todoDTOS = userService.getUserTodos(userId,status);
    return new ResponseEntity<List<TodoDTO>>(todoDTOS, HttpStatus.OK);
  }

  @PostMapping(value = "/createUser", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO request){
    LOGGER.info("Learning java - Procesando peticion HTTP de tipo POST - Starting...");
    ResponseDTO response = new ResponseDTO();
    //response = userService.createUser(request.getUser(), request.getPassword());
    //El método createUser es reemplazado por createUserMongo el cual aplica la conexión
    response = userService.createUserMongo(request);
    LOGGER.info("Create user - Completed");
    return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
  }

  // patrones de diseño
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
      //Aqui va la logica para obtener la informacion
      //Se regresa la respuesta normalmente
      return ResponseEntity.ok("It's ok");
    }

    //En caso de que se hayan hecho mas de 5 peticiones en 1 minuto respondera con este status
    return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
  }


}
