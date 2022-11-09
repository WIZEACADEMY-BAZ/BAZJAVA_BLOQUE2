package com.wizeline.DTO;

import java.util.Map;

public class UserDTO {
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

    public UserDTO getParameters(Map<String,String> userParams){
        UserDTO user = new UserDTO();
        user.setUser(userParams.get("user"));
        user.setPassword(userParams.get("password"));
        return user;
    }
}
