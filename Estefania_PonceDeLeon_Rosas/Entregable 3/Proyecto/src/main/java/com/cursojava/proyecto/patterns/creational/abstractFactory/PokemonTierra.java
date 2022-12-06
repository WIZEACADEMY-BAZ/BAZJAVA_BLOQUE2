package com.cursojava.proyecto.patterns.creational.abstractFactory;


import com.cursojava.proyecto.utils.herencia.Agua;
import com.cursojava.proyecto.utils.herencia.Movimiento;
import com.cursojava.proyecto.utils.herencia.Tierra;

public class PokemonTierra implements AprenderMovimiento{

    @Override
    public Movimiento aprenderMovimiento() {
        return new Agua();
    }
}
