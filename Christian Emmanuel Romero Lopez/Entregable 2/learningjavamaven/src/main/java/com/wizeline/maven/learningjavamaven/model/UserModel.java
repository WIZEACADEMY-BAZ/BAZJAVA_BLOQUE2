package com.wizeline.maven.learningjavamaven.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("userAccountCollection")
public class UserModel {

    private String user;
    private String password;

    /**
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password the password to set
     */
    public void setPassword(String password){
        this.password = password;
    }

    public UserModel getParameters(Map<String, String> userPram){
        UserModel user = new UserModel();
        user.setUser(userPram.get("user"));
        user.setPassword(userPram.get("password"));
        return user;
    }

}
