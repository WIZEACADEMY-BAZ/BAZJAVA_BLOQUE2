package com.cursojava.proyecto.services;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


public interface PokemonService {
    ResponseDTO createPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, String lastTraning);

    PokemonDTO getPokemonDetail(String nombre, String sonido, String tipo1, String tipo2, LocalDateTime lastTraning);
    Map<Integer, PokemonDTO> getTeam();


}
