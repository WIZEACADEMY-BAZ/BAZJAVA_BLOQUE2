package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.Enum.Tipo;
import com.cursojava.proyecto.model.PokemonDTO;

import java.time.LocalDateTime;

public interface PokemonDAO {

    String createPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, LocalDateTime lastTranning);
}
