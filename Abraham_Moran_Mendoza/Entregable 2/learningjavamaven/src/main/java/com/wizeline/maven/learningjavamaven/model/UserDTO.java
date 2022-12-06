package com.wizeline.maven.learningjavamaven.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("userCollection")
public class UserDTO {
  private String user;
  private String password;

  public UserDTO(){}

  public UserDTO(String user, String password){
    this.user = user;
    this.password = password;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
