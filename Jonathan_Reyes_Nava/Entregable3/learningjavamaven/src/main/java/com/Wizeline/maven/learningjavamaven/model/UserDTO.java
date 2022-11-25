package com.Wizeline.maven.learningjavamaven.model;

import com.Wizeline.maven.learningjavamaven.utils.Utils;

import java.util.Map;

public class UserDTO {

    private String user;

    private String password;


    public long id = Utils.randomAccountNumber();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser(){
        return user;}


    public void setUser(String user){
        this.user = user;}


    public String getPassword() { return password;}


    public void setPassword(String password) { this.password = password; }

  /*  public UserDTO getParameters(Map<String, String> userParam) {
        UserDTO user = new UserDTO();
        user.setUser(userParam.get("user"));
        user.setPassword(userParam.get("password"));
        return  user;
    } */

    public UserDTO(){}

    public UserDTO(String user, String password){
        this.user = user;
        this.password = password;
    }

}
