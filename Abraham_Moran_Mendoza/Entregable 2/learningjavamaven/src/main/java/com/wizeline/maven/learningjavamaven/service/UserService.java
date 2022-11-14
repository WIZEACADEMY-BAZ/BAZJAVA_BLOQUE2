package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import org.apache.coyote.Response;

import java.util.List;

public interface UserService {
  //ResponseDTO createUser(String user, String password);

  ResponseDTO createUserMongo(UserDTO userDTO);

  ResponseDTO getUserMongo(String user, String password);

  //ResponseDTO login(String user, String password);

  List<TodoDTO> getUserTodos(String userId);

  List<TodoDTO> getUserTodos(String userId, boolean status);

  List<PostDTO> getUserPosts(String userId);

}
