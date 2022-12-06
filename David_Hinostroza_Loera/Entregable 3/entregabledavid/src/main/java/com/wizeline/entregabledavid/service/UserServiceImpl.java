package com.wizeline.entregabledavid.service;

import com.wizeline.entregabledavid.builder.User;
import com.wizeline.entregabledavid.iterator.UserDTOCollection;
import com.wizeline.entregabledavid.iterator.UserDTOCollectionImpl;
import com.wizeline.entregabledavid.model.UserDTO;
import com.wizeline.entregabledavid.repository.UserRepository;
import com.wizeline.entregabledavid.repository.UserRepositoryImpl;
import com.wizeline.entregabledavid.model.ErrorDTO;
import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.login(user, password);
        }
        if ("success".equals(result)){
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            response.setErrors(new ErrorDTO("ER001",result));
            response.setStatus("fail");
        }
        return response;
    }

    @Override
    public User getUserBuild() {

        User user1 = new User.UserBuilder()
                .firstName("Jane")
                .lastName("Doe")
                .age(30)
                .phone("1234567")
                .address("Fake address 1234")
                .gender("M")
                .build();

        System.out.println(user1);
        return user1;
        /*User user2 = new User.UserBuilder()
                .firstName("Mary")
                .lastName("Jane")
                .age(40)
                .phone("5655")
                //no address
                .build();

        System.out.println(user2);

        User user3 = new User.UserBuilder()
                .firstName("Jane")
                .lastName("Doe")
                //No age
                //No phone
                //no address
                .build();

        System.out.println(user3);*/

    }

    @Override
    public UserDTOCollection populateUsers() {
        UserDTOCollection users = new UserDTOCollectionImpl();
        users.addUser(new UserDTO("user", "pstring"));
        users.addUser(new UserDTO("user", "pstring"));
        users.addUser(new UserDTO("user", "pstring"));
        users.addUser(new UserDTO("user2", "pstring"));
        users.addUser(new UserDTO("user2", "pstring"));
        return users;
    }
}
