package com.cursojava.proyecto.utils.threads;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.services.PokemonService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class CreatePokemons extends Thread{


    @Autowired
    PokemonService pokemonService;

    private JSONObject pokemonObject;

    private PokemonDTO pokemonDTO;
    public CreatePokemons( JSONObject pokemonObject){
        this.pokemonObject=pokemonObject;
    }
    public PokemonDTO getPokemon(){
        return this.pokemonDTO;
    }
    @Override
    public void run(){

        this.pokemonDTO = new PokemonDTO();
        this.pokemonDTO.setNombre(this.pokemonObject.getString("nombre"));
        this.pokemonDTO.setSonido(this.pokemonObject.getString("sonido"));
        this.pokemonDTO.setTipo1(Tipo.valueOf(this.pokemonObject.getString("tipo1")));

    }
}
