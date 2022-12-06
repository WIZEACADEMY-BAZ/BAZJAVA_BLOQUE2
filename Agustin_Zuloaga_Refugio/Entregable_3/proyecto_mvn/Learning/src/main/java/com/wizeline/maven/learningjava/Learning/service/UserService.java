package com.wizeline.maven.learningjava.Learning.service;
import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import com.wizeline.maven.learningjava.Learning.model.UserDateDTO;
import java.util.List;
public interface UserService {


    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);
    List<UserDateDTO> getUserDateIterator(String userId);

}
