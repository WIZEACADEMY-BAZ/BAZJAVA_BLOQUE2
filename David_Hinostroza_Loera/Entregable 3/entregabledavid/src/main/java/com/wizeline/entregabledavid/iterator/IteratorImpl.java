package com.wizeline.entregabledavid.iterator;

import com.wizeline.entregabledavid.model.UserDTO;

import java.util.List;

public class IteratorImpl implements Iterator {

    private String user;
    private List<UserDTO> users;
    private int position;

    public IteratorImpl(String user, List<UserDTO> usersList) {
        this.user = user;
        this.users = usersList;
    }

    @Override
    public boolean hasNext() {
        while (position < users.size()) {
            UserDTO u = users.get(position);
            if (u.getUser().equals(user) || user.equals("ALL")) {
                return true;
            } else
                position++;
        }
        return false;
    }

    @Override
    public UserDTO next() {
        UserDTO u = users.get(position);
        position++;
        return u;
    }
}