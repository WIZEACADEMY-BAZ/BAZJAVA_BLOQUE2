package com.wizeline.maven.learningjavamaven.model;

import java.util.Map;
import java.util.logging.Logger;

public class UserDTO {

    private static final Logger LOGGER = Logger.getLogger(UserDTO.class.getName());
    private String user;

    private String password;

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }


    public UserDTO getParameters(Map<String, String> userParam) {
        //System.out.println("Usuario loguado correctamente...  ");
        LOGGER.info("Usuario loguado correctamente...");
        UserDTO user = new UserDTO();
        user.setUser(userParam.get("user"));
        user.setPassword(userParam.get("password"));
        return user;
    }
    //constructructor recibiendo un parametro
    public UserDTO(String user, String password) {
        this.user = user;
        this.password = password;

    }
    //constructor vacion
    public UserDTO() {

    }

}