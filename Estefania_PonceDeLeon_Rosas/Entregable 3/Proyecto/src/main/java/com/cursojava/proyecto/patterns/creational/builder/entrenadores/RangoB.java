package com.cursojava.proyecto.patterns.creational.builder.entrenadores;

import com.cursojava.proyecto.model.EntrenadorDTO;

public class RangoB extends Enfrentamiento {
    public RangoB(){
        super();
        EntrenadorDTO entrenador1=new EntrenadorDTO("Felipe","B");
        EntrenadorDTO entrenador2=new EntrenadorDTO("Diana","B");
        this.entrenadores.add(entrenador1);
        this.entrenadores.add(entrenador2);
    }
}
