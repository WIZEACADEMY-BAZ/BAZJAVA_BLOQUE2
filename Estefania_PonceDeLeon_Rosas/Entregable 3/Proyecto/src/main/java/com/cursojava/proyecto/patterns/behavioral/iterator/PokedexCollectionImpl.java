package com.cursojava.proyecto.patterns.behavioral.iterator;

import com.cursojava.proyecto.model.PokemonDTO;

import java.util.ArrayList;
import java.util.List;

public class PokedexCollectionImpl implements PokedexCollection {

    private List<PokemonDTO> pokemonList;

    public PokedexCollectionImpl() {
        pokemonList = new ArrayList<>();
    }


    @Override
    public void addPokemon(PokemonDTO p) {
        this.pokemonList.add(p);
    }

    @Override
    public void eliminarPokemon(PokemonDTO p) {
        this.pokemonList.remove(p);
    }

    @Override
    public Iterator iterator(String type) {
        return new IteratorImpl(type, this.pokemonList);
    }
}
