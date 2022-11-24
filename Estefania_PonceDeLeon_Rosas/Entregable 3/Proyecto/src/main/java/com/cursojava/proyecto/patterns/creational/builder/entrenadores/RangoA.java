package com.cursojava.proyecto.patterns.creational.builder.entrenadores;

import com.cursojava.proyecto.model.EntrenadorDTO;

public class RangoA extends Enfrentamiento {

    public RangoA(){
        super();
        EntrenadorDTO entrenador1=new EntrenadorDTO("Jose","A");
        this.entrenadores.add(entrenador1);
    }
}
