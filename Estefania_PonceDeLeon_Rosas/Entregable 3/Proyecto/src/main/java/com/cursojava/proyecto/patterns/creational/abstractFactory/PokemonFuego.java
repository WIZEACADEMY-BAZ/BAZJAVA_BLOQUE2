package com.cursojava.proyecto.patterns.creational.abstractFactory;

import com.cursojava.proyecto.utils.herencia.Fuego;
import com.cursojava.proyecto.utils.herencia.Movimiento;

public class PokemonFuego implements AprenderMovimiento{

    @Override
    public Movimiento aprenderMovimiento() {
        return new Fuego();
    }
}
