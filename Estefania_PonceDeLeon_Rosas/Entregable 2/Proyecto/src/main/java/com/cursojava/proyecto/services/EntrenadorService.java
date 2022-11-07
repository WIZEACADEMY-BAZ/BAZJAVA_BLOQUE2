package com.cursojava.proyecto.services;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.model.PokemonDTO;

import java.util.List;

public interface EntrenadorService {

    void registrarDatos(EntrenadorDTO entrenador);
    EntrenadorDTO consultarInformacion(EntrenadorDTO entrenador);
    void registrarEquipo(EntrenadorDTO entrenador, PokemonDTO[] equipo);
    void retirarse(String nombre, String password);
    List<EntrenadorDTO> getEncryptedTrainers();

}
