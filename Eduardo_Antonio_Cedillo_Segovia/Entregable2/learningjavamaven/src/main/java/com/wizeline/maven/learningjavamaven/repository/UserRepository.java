package com.wizeline.maven.learningjavamaven.repository;

//Se crea una interfaz
public interface UserRepository {

   public String createUser(String user, String password);

   public String login (String user, String password);
}
