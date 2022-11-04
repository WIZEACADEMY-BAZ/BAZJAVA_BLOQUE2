package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.TipoDTO;

import java.util.Collection;

public interface TipoService {

    public void initTypes();
    public void deleteAll();

    public Collection<TipoDTO> getAll();

}
