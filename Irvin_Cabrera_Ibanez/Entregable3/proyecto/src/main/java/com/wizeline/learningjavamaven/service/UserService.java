package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.model.UserDateDTO;

import java.util.List;

public interface UserService {

  ResponseDTO createUser(String username, String password);
  ResponseDTO login(String username, String password);
  ResponseDTO updateUser(String username, String password);
  ResponseDTO deleteUser(String username);
  UserDTO getUser(String username);
  List<UserDateDTO> getUserDateIterator(String userId);
}
