package com.cursojava.proyecto.utils.herencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Movimiento {

    protected List<String> movimientos;

    protected Movimiento(){
        this.movimientos = new ArrayList<>();
    }

    public String action (){
        Random rnd = new Random();
        int r = rnd.nextInt(movimientos.size());
        return movimientos.get(r);
    }

    public List<String> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<String> movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "movimientos=" + movimientos +
                '}';
    }

    public String getMovimientoAleatorio(){
        int index =  (int)Math.floor(Math.random()*(this.movimientos.size()));
        return this.movimientos.get(index);
    }
}
