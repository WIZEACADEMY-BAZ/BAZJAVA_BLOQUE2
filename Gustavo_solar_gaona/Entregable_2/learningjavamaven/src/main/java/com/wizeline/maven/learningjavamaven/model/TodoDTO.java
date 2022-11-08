package com.wizeline.maven.learningjavamaven.model;

public class TodoDTO {
  private Integer id;
  private Integer userId;
  private String title;
  private Boolean completed;

  private String userName;

  public TodoDTO(){
  }

  public TodoDTO(Integer id
      , Integer userId
      , String title
      , Boolean completed){
    this.id = id;
    this.userId = userId;
    this.title = title;
    this.completed = completed;
  }

  // Revisión: Sobrecarga de al menos uno de los constructores de alguna clase
  public TodoDTO(Integer id
      , String userName
      , String title
      , Boolean completed){
    this.id = id;
    this.userName = userName;
    this.title = title;
    this.completed = completed;
  }

  // Revisión: Encapsulamiento de al menos una clase
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public void setCompleted(Boolean completed) {
    this.completed = completed;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
