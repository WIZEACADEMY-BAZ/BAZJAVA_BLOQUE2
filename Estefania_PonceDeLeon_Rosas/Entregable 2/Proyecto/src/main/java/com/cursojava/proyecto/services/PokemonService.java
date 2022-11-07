package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface PokemonService {
    ResponseDTO createPokemon(PokemonDTO pokemon);
    Map<Integer, PokemonDTO> getDefaultTeam();
    void deleteAll();
    List<PokemonDTO> getAllPokemons();
    ResponseDTO getTotalByType(String type);
    PokeApiPokemon jsonApiExterna(String url);

}
