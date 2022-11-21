package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.model.UserDateDTO;
import com.wizeline.learningjavamaven.patron.Iterator.Iterator;
import com.wizeline.learningjavamaven.patron.Iterator.UserDateDTOCollection;
import com.wizeline.learningjavamaven.patron.Iterator.UserDateDTOCollectionImpl;
import com.wizeline.learningjavamaven.repository.UserRepository;
import com.wizeline.learningjavamaven.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

  private static final String ERROR_CODE_1 = "ER001";
  private static final String ERROR_CODE_2 = "ER002";
  private static final String CODE = "OK000";
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  @Autowired
  UserRepository userRepository;

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public ResponseDTO createUser(String username, String password) {
    LOGGER.info("Inicia procesamiento en capa de negocio");
    ResponseDTO response = new ResponseDTO();
    if (Utils.validateNullValue(username) && Utils.validateNullValue(password)) {
      UserDTO userExist = getUser(username);
      if (userExist != null) {
        response.setStatus(FAIL);
        response.setCode(CODE);
        response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_2, "Usuario existente"));
      } else {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        mongoTemplate.save(userDTO);
        response.setCode(CODE);
        response.setStatus(SUCCESS);
      }
    } else {
      response.setCode(CODE);
      response.setStatus(FAIL);
      response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_1, "Error al crear usuario"));
    }
    return response;
  }

  @Override
  public ResponseDTO login(String username, String password) {
    LOGGER.info("Inicia procesamiento en capa de negocio");
    ResponseDTO response = new ResponseDTO();
    UserDTO userDTO = null;
    if (Utils.validateNullValue(username) && Utils.validateNullValue(password)) {
      userDTO = getUserAndPassword(username, password);
    }
    if (userDTO != null) {
      response.setCode(CODE);
      response.setStatus(SUCCESS);
    } else {
      response.setCode(ERROR_CODE_1);
      response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_1, "nulo"));
      response.setStatus(FAIL);
    }
    return response;
  }

  @Override
  public ResponseDTO updateUser(String username, String password) {
    ResponseDTO response = new ResponseDTO();
    UserDTO userDTO = getUser(username);
    if (userDTO != null) {
      userDTO.setPassword(password);
      mongoTemplate.save(userDTO);
      response.setCode(CODE);
      response.setStatus(SUCCESS);
    } else {
      response.setCode(ERROR_CODE_1);
      response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_1, "No existe el usuario"));
      response.setStatus(FAIL);
    }
    return response;
  }

  @Override
  public ResponseDTO deleteUser(String username) {
    ResponseDTO response = new ResponseDTO();
    UserDTO userDTO = getUser(username);
    if (userDTO != null) {
      Optional<UserDTO> result = userRepository.findById(userDTO.getId());
      if (result.isPresent()) {
        userRepository.delete(result.get());
        response.setCode(CODE);
        response.setStatus(SUCCESS);
      } else {
        response.setCode(ERROR_CODE_1);
        response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_1, "No existe el usuario"));
        response.setStatus(FAIL);
      }
    } else {
      response.setCode(ERROR_CODE_1);
      response.setErrors(new ResponseDTO.ErrorDTO(ERROR_CODE_1, "No existe el usuario"));
      response.setStatus(FAIL);
    }
    return response;
  }

  public UserDTO getUser(String username) {
    Query query = new Query();
    query.addCriteria(Criteria.where("username").is(username));
    return mongoTemplate.findOne(query, UserDTO.class);
  }

  private UserDTO getUserAndPassword(String username, String password) {
    Query query = new Query();
    query.addCriteria(Criteria.where("username").is(username));
    query.addCriteria(Criteria.where("password").is(password));
    return mongoTemplate.findOne(query, UserDTO.class);
  }

  @Override
  public List<UserDateDTO> getUserDateIterator(String userId){
    List<UserDateDTO> userDateDTOList = new ArrayList<>();
    UserDateDTOCollection users = fechasUsuarios();

    Iterator baseIterator = users.iterator(userId);
    while(baseIterator.hasNext()){
      UserDateDTO userDateDTO = baseIterator.next();
      System.out.println(userDateDTO.toString());
      userDateDTOList.add(userDateDTO);
    }
    System.out.println("**********");

    return userDateDTOList;
  }

  private static UserDateDTOCollection fechasUsuarios() {
    UserDateDTOCollection userDateDTOCollection = new UserDateDTOCollectionImpl();
    userDateDTOCollection.addUserDate(new UserDateDTO("1", "12", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("2", "18", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("3", "22", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("4", "19", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("5", "35", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("6", "29", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("7", "32", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("8", "40", "10-01-1990"));
    userDateDTOCollection.addUserDate(new UserDateDTO("9", "25", "10-01-1990"));
    return userDateDTOCollection;
  }
}
