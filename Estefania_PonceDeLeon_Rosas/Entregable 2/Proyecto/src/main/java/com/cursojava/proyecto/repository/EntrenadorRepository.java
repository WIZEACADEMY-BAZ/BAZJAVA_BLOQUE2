package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends MongoRepository<EntrenadorDTO, Long> {

    EntrenadorDTO findEntrenadorDTOByNombreAndPassword (String nombre, String password);
    void deleteEntrenadorDTOByNombreAndClaveDeSeguridad(String nombre, String claveDeSeguridad);

}
