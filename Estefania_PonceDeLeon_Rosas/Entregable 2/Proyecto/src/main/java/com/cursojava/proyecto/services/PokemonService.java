package com.cursojava.proyecto.services;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;


public interface PokemonService {
    ResponseDTO createPokemon(PokemonDTO pokemon);
    Map<Integer, PokemonDTO> getTeam();


}
