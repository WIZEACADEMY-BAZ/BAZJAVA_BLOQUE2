package com.cursojava.proyecto.patterns.behavioral.iterator;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.TipoDTO;

import java.util.List;

public class IteratorImpl implements Iterator {

    private List<PokemonDTO> pokemonList;
    private int position;

    private String tipo;
    public IteratorImpl(String tipo, List<PokemonDTO> pokemonList) {
        this.tipo= tipo;
        this.pokemonList = pokemonList;
        this.position=0;
    }

    @Override
    public boolean hasNext() {
        while (position < pokemonList.size()) {
            PokemonDTO p = pokemonList.get(position);
            if (p.getTipo1().getNombre().equals(this.tipo) || this.tipo.equals("ALL")) {
                return true;
            } else
                position++;
        }
        return false;
    }

    @Override
    public PokemonDTO next() {
        PokemonDTO c = this.pokemonList.get(position);
        position++;
        return c;
    }

}