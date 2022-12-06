package com.cursojava.proyecto.repository;
import com.cursojava.proyecto.model.PokemonDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends MongoRepository<PokemonDTO, Long> { }