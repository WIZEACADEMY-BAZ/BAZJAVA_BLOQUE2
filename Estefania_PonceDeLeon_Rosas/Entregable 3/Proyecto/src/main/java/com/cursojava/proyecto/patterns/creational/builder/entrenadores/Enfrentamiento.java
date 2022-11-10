package com.cursojava.proyecto.patterns.creational.builder.entrenadores;


import com.cursojava.proyecto.model.EntrenadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Enfrentamiento {
    private static final Logger LOGGER = LoggerFactory.getLogger(Enfrentamiento.class);
    public List<EntrenadorDTO> entrenadores;
    private EntrenadorDTO vencedor;
    public Enfrentamiento(){
       this.entrenadores = new ArrayList<>();
    }
    public EntrenadorDTO getVencedor(){
        return this.vencedor;
    }
    public void setVencedor (EntrenadorDTO vencedor) {
        this.vencedor=vencedor;
    }

    public void verCompeticion(){
        LOGGER.info("En esta ronda hay " + entrenadores.size() + " competidores");
        int rd =  (int)Math.floor(Math.random()*(entrenadores.size()-1));
        setVencedor(entrenadores.get(rd));
        LOGGER.info("El vencedor de esta ronda es: " +vencedor.getNombre());
    }
}