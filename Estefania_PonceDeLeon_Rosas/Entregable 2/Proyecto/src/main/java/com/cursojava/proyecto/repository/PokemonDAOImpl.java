package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.Enum.Tipo;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Repository
public class PokemonDAOImpl implements PokemonDAO{


    @Override
    public String createPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, LocalDateTime lastTraning) {
        PokemonDTO pokemon = new PokemonDTO();
        pokemon.setNombre(nombre);
        pokemon.setSonido(sonido);
        pokemon.setTipo1(tipo1);
        pokemon.setTipo2(Optional.ofNullable(tipo2));
        pokemon.setEvolucion(Optional.ofNullable(evolucion));
        pokemon.setLastTraning(Optional.ofNullable(lastTraning));
        return "success";
    }
}
