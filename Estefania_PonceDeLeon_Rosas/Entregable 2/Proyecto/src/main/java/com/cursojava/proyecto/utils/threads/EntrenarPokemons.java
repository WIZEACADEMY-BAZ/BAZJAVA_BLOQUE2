package com.cursojava.proyecto.utils.threads;

import com.cursojava.proyecto.model.PokemonDTO;

import java.util.logging.Logger;

public class EntrenarPokemons extends Thread {
    private static final Logger LOGGER = Logger.getLogger(EntrenarPokemons.class.getName());
    private PokemonDTO pokemon;

    public EntrenarPokemons(){}
    public EntrenarPokemons(PokemonDTO pokemon) {
        this.pokemon = pokemon;
    }

    public PokemonDTO getPokemon() {
        return pokemon;
    }

    public void setPokemon(PokemonDTO pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public void run() {
        this.pokemon.setStatus("Entrenado...");
    }
}
