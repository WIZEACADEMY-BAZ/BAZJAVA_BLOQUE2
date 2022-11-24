package com.wizeline.maven.learningjavagmaven.service;

import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.repository.UserImpl;
import com.wizeline.maven.learningjavagmaven.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.Max;

import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userServiceImp;

    @Mock
    private  UserService userService;

    @Mock
    private ResponseModel responseModel;

    @Test
    void createUserTest() {
        ResponseModel response = new ResponseModel();
        UserRepository userRepository = new UserImpl();
        String result;

        result = userRepository.createUser("user1@wizeline.com","pass1");
  //      response=userRepository.createUser("user1@wizeline.com","pass1");

        //response.setStatus(result);
        //response.setCode("OK000");
        System.out.println(response.getCode() + "  " + response.getStatus());
        System.out.println(result);
        assertEquals( "OK000",response.getCode());

    }

    @Test
    void login() {
    }
}