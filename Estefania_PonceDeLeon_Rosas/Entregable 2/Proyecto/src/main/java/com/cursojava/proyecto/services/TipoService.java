package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.TipoDTO;

import java.util.Collection;

public interface TipoService {

    void initTypes();
    void deleteAll();

    Collection<TipoDTO> getAll();

}
