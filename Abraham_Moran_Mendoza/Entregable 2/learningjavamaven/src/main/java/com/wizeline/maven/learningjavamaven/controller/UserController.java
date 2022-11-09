package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  // Revisión: API generada por Spring MVC con un endpoint de cada tipo (GET, POST, PUT, DELETE)
  @GetMapping(value = "/login", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<ResponseDTO> login(@RequestParam String user, String password){
    LOGGER.info( PROCESSING_GET_METHOD);
    ResponseDTO response = new ResponseDTO();
    //response = userService.login(user, password);
    //El método login es reemplazado por userMongo el cual aplica la conexión
    response = userService.getUserMongo(user, password);
    LOGGER.info("Login - Completed");
    return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
  }

  // Revisión: Segurizar dos endpoints con acceso basado en roles protegidos con el token generado
  @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
  // Revisión: Consumo de API pública usando RestTemplate
  @GetMapping(value = "/users/{userId}/todos", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<TodoDTO>> getGetUserTodos(@PathVariable("userId") String userId){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<TodoDTO> todoDTOS = userService.getUserTodos(userId);
    return new ResponseEntity<List<TodoDTO>>(todoDTOS, HttpStatus.OK);
  }

  // Revisión: Consumo de API pública usando RestTemplate
  @PreAuthorize("hasAnyRole('USER','GUEST','ADMIN')")
  @GetMapping(value = "/users/{userId}/posts", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<PostDTO>> getGetUserPosts(@PathVariable("userId") String userId){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<PostDTO> postDTOS = userService.getUserPosts(userId);
    return new ResponseEntity<List<PostDTO>>(postDTOS, HttpStatus.OK);
  }

  // Revisión: Segurizar dos endpoints con acceso basado en roles protegidos con el token generado
  @PreAuthorize("hasRole('USER')")
  // Revisión: Sobrecarga de al menos uno de los métodos de alguna clase
  @GetMapping(value = "/users/{userId}/filteredTodos", produces = CONTENT_TYPE_JSON)
  public ResponseEntity<List<TodoDTO>> getGetUserTodos(
      @PathVariable("userId") String userId
      ,@RequestParam("status") boolean status ){
    LOGGER.info( PROCESSING_GET_METHOD);
    List<TodoDTO> todoDTOS = userService.getUserTodos(userId,status);
    return new ResponseEntity<List<TodoDTO>>(todoDTOS, HttpStatus.OK);
  }

  // Revisión: API generada por Spring MVC con un endpoint de cada tipo (GET, POST, PUT, DELETE)
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



}
