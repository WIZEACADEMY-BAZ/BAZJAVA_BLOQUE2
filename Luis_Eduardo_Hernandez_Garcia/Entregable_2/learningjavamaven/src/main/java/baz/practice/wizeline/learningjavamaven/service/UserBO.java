package baz.practice.wizeline.learningjavamaven.service;

//import com.wizeline.DTO.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;

public interface UserBO {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

}
