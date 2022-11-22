package com.wizeline.entregabledavid.iterator;


import com.wizeline.entregabledavid.model.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserDTOCollectionImpl implements UserDTOCollection {

    private List<UserDTO> usersList;

    public UserDTOCollectionImpl() {
        usersList = new ArrayList<>();
    }

    public void addUser(UserDTO c) {
        this.usersList.add(c);
    }

    public void removeUser(UserDTO c) {
        this.usersList.remove(c);
    }

    @Override
    public Iterator iterator(String user) {
        return new IteratorImpl(user, this.usersList);
    }
}