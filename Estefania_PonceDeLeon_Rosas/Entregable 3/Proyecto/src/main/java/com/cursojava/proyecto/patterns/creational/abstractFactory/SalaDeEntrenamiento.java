package com.cursojava.proyecto.patterns.creational.abstractFactory;

import com.cursojava.proyecto.utils.herencia.Movimiento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SalaDeEntrenamiento {
    private static final Logger LOGGER = LoggerFactory.getLogger(SalaDeEntrenamiento.class);
    private Movimiento movimiento;

    public SalaDeEntrenamiento(AprenderMovimiento movimientoFactory){
        this.movimiento=movimientoFactory.aprenderMovimiento();
    }

    public void Entrenar(){
        LOGGER.info("Enseñando movimiento: "+this.movimiento.getMovimientoAleatorio());
    }
}
