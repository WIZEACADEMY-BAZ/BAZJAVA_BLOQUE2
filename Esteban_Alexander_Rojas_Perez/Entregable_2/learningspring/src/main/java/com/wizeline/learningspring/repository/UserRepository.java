package com.wizeline.learningspring.repository;

public interface UserRepository {
    String createUser(String user, String password);
    String login(String user, String password);
    void createFile();
    void writeFile(String user, String password);
    String readFile(String user, String password);
}
