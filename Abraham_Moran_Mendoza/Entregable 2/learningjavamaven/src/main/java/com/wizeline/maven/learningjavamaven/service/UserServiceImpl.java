package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.*;
import com.wizeline.maven.learningjavamaven.repository.PostRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  @Autowired
  PostRepository postRepository;

  private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

  /*@Override
  public ResponseDTO createUser(String user, String password) {
    LOGGER.info(INIT_BUSINESS_LAYER);
    ResponseDTO response = new ResponseDTO();
    String result = "fail";
    System.out.println("Prueba 1");
    if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
      UserRepository userRepository = new UserRepositoryImpl();
      result = userRepository.createUser(user, password);
      response.setCode(SUCCESS_CODE);
      response.setStatus(result);
      System.out.println("Prueba 2");
    }else {
      System.out.println("Prueba 3");
      response.setCode(SUCCESS_CODE);
      response.setStatus(result);
      response.setErrors(new ErrorDTO(ERROR_CODE,"Error al crear usuario"));
    }
    return response;
  }*/

  @Override
  public ResponseDTO createUserMongo(UserDTO userDTO){
    UserDTO userDTOOptional = userRepository.save(userDTO);
    ResponseDTO response = new ResponseDTO();
    if(userDTOOptional != null){
      response.setCode(SUCCESS_CODE);
      response.setStatus(SUCCESS_STATUS);
    }else{
      response.setCode(ERROR_CODE);
      response.setErrors(new ErrorDTO(ERROR_CODE,"No se pudo crear el usuario"));
      response.setStatus("No se pudo crear el usuario");
    }
    return response;
  }

  @Override
  public ResponseDTO getUserMongo(String user, String password){
    //Buscamos todos aquellos registros de tipo UserDTO
    //que cumplen con la criteria de que el userName  y password hagan match
    //con la variable user
    System.out.println("Paso aqui");
    System.out.println(user);
    System.out.println(password);
    Query query = new Query();
    query.addCriteria(Criteria.where("user").is(user).and("password").is(password));
    UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);
    System.out.println(userDTO);
    ResponseDTO response = new ResponseDTO();
    System.out.println("Paso aqui");
    if(userDTO != null){
      response.setCode(SUCCESS_CODE);
      response.setStatus(SUCCESS_STATUS);
    }else{
      response.setCode(ERROR_CODE);
      response.setErrors(new ErrorDTO(ERROR_CODE,"Usuario o password incorrecto"));
      response.setStatus("Usuario o password incorrecto");
    }
    return response;
  }

  /*@Override
  public ResponseDTO login(String user, String password) {
    LOGGER.info(INIT_BUSINESS_LAYER);
    ResponseDTO response = new ResponseDTO();
    String result = "";
    if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
      UserRepository userRepository = new UserRepositoryImpl();
      result = userRepository.login(user,password);
    }
    if(SUCCESS_STATUS.equals(result)) {
      response.setCode(SUCCESS_CODE);
      response.setStatus(result);
    } else {
      response.setCode(ERROR_CODE);
      response.setErrors(new ErrorDTO(ERROR_CODE,result));
      response.setStatus("fail");
    }
    return response;
  }*/

  @Override
  public List<TodoDTO> getUserTodos(String userId){
    String url = JSON_TODOS_API_URL.replace("?", userId);
    RestTemplate restTemplate = new RestTemplate();
    // Revisión: Uso de por lo menos un arreglo
    return Arrays.asList(restTemplate.getForObject(url, TodoDTO[].class));
  }

  @Override
  public List<TodoDTO> getUserTodos(String userId, boolean status){
    // Revisión: Uso de por lo menos una lista
    List<TodoDTO> todoDTOS = getUserTodos(userId);
    TodosHelper todosHelper = new TodosHelper();
    return todosHelper.covertDTOs(todoDTOS, status);
  }

  @Override
  public List<PostDTO> getUserPosts(String userId){
    // Revisión: Uso de por lo menos una lista
    return postRepository.getUserPosts(userId);
  }

  // Revisión: Clase interna dentro de al menos una clase
  class TodosHelper{
    List<TodoDTO> covertDTOs(List<TodoDTO> todoDTOS, boolean status){
      // Revisión: Uso de por lo menos 2 operaciones intermedias y 2 tipos de colectores en un Stream
      return todoDTOS.stream().filter( (TodoDTO todoDTO) ->
          todoDTO.getCompleted() == status
      ).map( (TodoDTO todoDTO) -> {
        if(todoDTO.getUserId() == 1){
          return new TodoDTO(todoDTO.getId(), "Abraham Moran", todoDTO.getTitle(), todoDTO.getCompleted());
        }
        return new TodoDTO(todoDTO.getId(), todoDTO.getUserId(), todoDTO.getTitle(), todoDTO.getCompleted());
      }).collect(Collectors.toList());
    }
  }
}
