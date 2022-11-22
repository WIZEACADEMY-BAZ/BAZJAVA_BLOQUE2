package com.wizeline.learningjavamaven.model;

import com.wizeline.learningjavamaven.model.detalle.UserData;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Map;

@Document("userCollection")
public class UserDTO extends UserData {

  @Id
  private String id;

  private String password;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserDTO getParameters(Map<String, String> userParam) {
    UserDTO user = new UserDTO();
    user.setUsername(userParam.get("username"));
    user.setPassword(userParam.get("password"));
    return user;
  }
}
