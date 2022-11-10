package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.TipoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends MongoRepository<TipoDTO, Long> { }
