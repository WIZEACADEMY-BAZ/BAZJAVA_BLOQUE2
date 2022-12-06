package com.wizeline.entregabledavid.service;

import com.wizeline.entregabledavid.builder.User;
import com.wizeline.entregabledavid.iterator.UserDTOCollection;
import com.wizeline.entregabledavid.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

    User getUserBuild ();

    UserDTOCollection populateUsers();

}
