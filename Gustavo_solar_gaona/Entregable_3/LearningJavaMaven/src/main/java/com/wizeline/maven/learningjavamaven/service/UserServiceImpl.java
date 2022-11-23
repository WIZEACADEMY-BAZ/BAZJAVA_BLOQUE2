package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.*;
import com.wizeline.maven.learningjavamaven.repository.PostRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    Query query = new Query();
    query.addCriteria(Criteria.where("user").is(user).and("password").is(password));
    UserDTO userDTO = mongoTemplate.findOne(query, UserDTO.class);
    ResponseDTO response = new ResponseDTO();
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

  @Override
  public List<TodoDTO> getUserTodos(String userId){
    String url = JSON_TODOS_API_URL.replace("?", userId);
    RestTemplate restTemplate = new RestTemplate();
    return Arrays.asList(restTemplate.getForObject(url, TodoDTO[].class));
  }

  @Override
  public List<TodoDTO> getUserTodos(String userId, boolean status){
    List<TodoDTO> todoDTOS = getUserTodos(userId);
    TodosHelper todosHelper = new TodosHelper();
    return todosHelper.covertDTOs(todoDTOS, status);
  }

  @Override
  public List<PostDTO> getUserPosts(String userId){
    return postRepository.getUserPosts(userId);
  }

  class TodosHelper{
    List<TodoDTO> covertDTOs(List<TodoDTO> todoDTOS, boolean status){
      return todoDTOS.stream().filter( (TodoDTO todoDTO) ->
          todoDTO.getCompleted() == status
      ).map( (TodoDTO todoDTO) -> {
        if(todoDTO.getUserId() == 1){
          return new TodoDTO(todoDTO.getId(), "Gustavo Solar", todoDTO.getTitle(), todoDTO.getCompleted());
        }
        return new TodoDTO(todoDTO.getId(), todoDTO.getUserId(), todoDTO.getTitle(), todoDTO.getCompleted());
      }).collect(Collectors.toList());
    }
  }
}
