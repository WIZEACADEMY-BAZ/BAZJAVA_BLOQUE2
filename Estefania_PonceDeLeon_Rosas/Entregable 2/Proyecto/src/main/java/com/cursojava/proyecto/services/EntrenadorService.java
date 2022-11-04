package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;

public interface EntrenadorService {

    void registrarDatos(EntrenadorDTO entrenador);
    EntrenadorDTO consultarInformacion(EntrenadorDTO entrenador);
    void registrarEquipo(EntrenadorDTO entrenador, PokemonDTO[] equipo);
    void retirarse(String nombre, String password);

}
