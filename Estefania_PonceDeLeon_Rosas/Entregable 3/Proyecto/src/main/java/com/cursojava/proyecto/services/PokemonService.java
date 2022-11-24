package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.Pokeapi.PokeApiPokemon;


public interface PokemonService {
    PokeApiPokemon jsonApiExterna(String url);

}
