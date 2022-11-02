package com.cursojava.proyecto.repository;

import com.cursojava.proyecto.model.PokemonDTO;
import com.cursojava.proyecto.model.ResponseDTO;
import com.cursojava.proyecto.Enum.Tipo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

public interface PokemonDAO {

    String createPokemon(String nombre, String sonido, Tipo tipo1, Tipo tipo2, PokemonDTO evolucion, LocalDateTime lastTranning);
}
