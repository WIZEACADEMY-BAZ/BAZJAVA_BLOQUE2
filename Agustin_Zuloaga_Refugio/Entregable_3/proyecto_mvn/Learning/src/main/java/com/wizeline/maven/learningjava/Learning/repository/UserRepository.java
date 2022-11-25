package com.wizeline.maven.learningjava.Learning.repository;

public interface UserRepository {

    String createUser(String user, String password);

    String login(String user, String password);

}