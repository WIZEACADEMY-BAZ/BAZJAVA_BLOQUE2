package com.cursojava.proyecto.patterns.creational.builder.seleccion;

import com.cursojava.proyecto.patterns.creational.builder.entrenadores.RangoB;
import com.cursojava.proyecto.patterns.creational.builder.entrenadores.RangoC;

public class BloqueB extends SeleccionBuilder {
    @Override
    public void addCompetidores() {
        getEnfrentamiento().entrenadores.add(new RangoB().entrenadores.get(1));
        getEnfrentamiento().entrenadores.add(new RangoC().entrenadores.get(1));
        getEnfrentamiento().entrenadores.add(new RangoC().entrenadores.get(2));
    }
}
