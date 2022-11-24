package com.cursojava.proyecto.patterns.creational.builder.entrenadores;

import com.cursojava.proyecto.model.EntrenadorDTO;

public class RangoC extends Enfrentamiento {
    public RangoC(){
        super();
        EntrenadorDTO entrenador1=new EntrenadorDTO("Esmeralda","C");
        EntrenadorDTO entrenador2=new EntrenadorDTO("Christian","C");
        EntrenadorDTO entrenador3=new EntrenadorDTO("Juan","C");
        this.entrenadores.add(entrenador1);
        this.entrenadores.add(entrenador2);
        this.entrenadores.add(entrenador3);
    }
}
