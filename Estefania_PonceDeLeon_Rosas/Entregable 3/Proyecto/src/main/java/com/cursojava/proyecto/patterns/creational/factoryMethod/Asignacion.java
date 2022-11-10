package com.cursojava.proyecto.patterns.creational.factoryMethod;

import com.cursojava.proyecto.patterns.creational.factoryMethod.contrincantes.ContrincanteEstiloAgua;
import com.cursojava.proyecto.patterns.creational.factoryMethod.contrincantes.ContrincanteEstiloFuego;
import com.cursojava.proyecto.patterns.creational.factoryMethod.contrincantes.ContrincanteEstiloTierra;

public class Asignacion{

    public Contrincante encontrarContrincante(String estilo) {
        if (estilo == null || estilo.isEmpty())
            return null;
        switch (estilo) {
            case "Fuego":
                return new ContrincanteEstiloFuego();
            case "Tierra":
                return new ContrincanteEstiloTierra();
            case "Agua":
                return new ContrincanteEstiloAgua();
            default:
                throw new IllegalArgumentException("El estilo " + estilo+ " no está registrado");
        }
    }
}
