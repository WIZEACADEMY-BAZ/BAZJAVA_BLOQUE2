package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;

import java.time.LocalDateTime;

public interface PokemonDAO {

    ResponseDTO createPokemon(PokemonDTO pokemon);
}
