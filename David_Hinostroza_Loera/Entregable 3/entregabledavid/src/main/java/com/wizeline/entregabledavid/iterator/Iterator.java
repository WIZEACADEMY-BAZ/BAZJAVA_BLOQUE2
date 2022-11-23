package com.wizeline.entregabledavid.iterator;

import com.wizeline.entregabledavid.model.UserDTO;

public interface Iterator {

    boolean hasNext();

    UserDTO next();
}