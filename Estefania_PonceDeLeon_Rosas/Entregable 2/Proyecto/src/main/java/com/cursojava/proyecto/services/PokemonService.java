package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;

import java.util.Collection;
import java.util.Map;


public interface PokemonService {
    ResponseDTO createPokemon(PokemonDTO pokemon);
    Map<Integer, PokemonDTO> getDefaultTeam();

    void deleteAll();

    Collection<PokemonDTO> getAll();


}
