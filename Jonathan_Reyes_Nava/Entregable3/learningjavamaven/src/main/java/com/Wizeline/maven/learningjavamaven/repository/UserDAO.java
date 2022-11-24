package com.Wizeline.maven.learningjavamaven.repository;


public interface UserDAO {

    String createUser(String  user, String password);

    String login(String user, String password);




}
