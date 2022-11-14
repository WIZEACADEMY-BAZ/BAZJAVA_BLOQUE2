
package com.wizeline.maven.learningjavagmaven.model;

import java.util.Map;


public class UserModel {

    private String user;

    private String password;


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


    public UserModel getParameters(Map<String, String> userParam) {
        UserModel user = new UserModel();
        user.setUser(userParam.get("user"));
        user.setPassword(userParam.get("password"));
        return user;
    }



}