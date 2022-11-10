package com.cursojava.proyecto.patterns.behavioral.iterator;

import com.cursojava.proyecto.model.PokemonDTO;

public interface PokedexCollection{

    public void addPokemon(PokemonDTO p);

    public void eliminarPokemon(PokemonDTO p);
    public Iterator iterator(String type);

}
