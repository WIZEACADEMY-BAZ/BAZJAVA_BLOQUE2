package com.wizeline.DAO;

public interface UserDao {

    String createUser(String user, String password);

    String login(String user, String password);
}
