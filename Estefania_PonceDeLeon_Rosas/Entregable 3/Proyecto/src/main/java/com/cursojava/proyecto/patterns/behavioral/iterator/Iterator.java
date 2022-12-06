package com.cursojava.proyecto.patterns.behavioral.iterator;

import com.cursojava.proyecto.model.PokemonDTO;

public interface Iterator {

    boolean hasNext();

    PokemonDTO next();
}
