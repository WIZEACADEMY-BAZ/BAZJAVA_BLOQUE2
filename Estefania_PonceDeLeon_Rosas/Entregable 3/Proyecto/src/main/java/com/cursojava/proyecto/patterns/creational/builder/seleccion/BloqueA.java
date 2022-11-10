package com.cursojava.proyecto.patterns.creational.builder.seleccion;


import com.cursojava.proyecto.patterns.creational.builder.entrenadores.RangoA;
import com.cursojava.proyecto.patterns.creational.builder.entrenadores.RangoB;
import com.cursojava.proyecto.patterns.creational.builder.entrenadores.RangoC;

public class BloqueA extends SeleccionBuilder {

    @Override
    public void addCompetidores() {
        getEnfrentamiento().entrenadores.add(new RangoA().entrenadores.get(0));
        getEnfrentamiento().entrenadores.add(new RangoB().entrenadores.get(0));
        getEnfrentamiento().entrenadores.add(new RangoC().entrenadores.get(0));
    }


}
