package com.wizeline.entregabledavid.iterator;

import com.wizeline.entregabledavid.model.UserDTO;

public interface UserDTOCollection {

    public void addUser(UserDTO u);

    public void removeUser(UserDTO c);

    public Iterator iterator(String user);
}
