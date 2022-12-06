package com.cursojava.proyecto.patterns.creational.builder;

import com.cursojava.proyecto.patterns.creational.builder.seleccion.SeleccionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Direccion {

    private static final Logger LOGGER = LoggerFactory.getLogger(Direccion.class);
    private SeleccionBuilder seleccionBuilder;
    private String ronda;
    public void setSeleccionBuilder (SeleccionBuilder seleccionBuilder) {
        this.seleccionBuilder = seleccionBuilder;
    }

    public void iniciarRonda(String ronda) {
        this.ronda=ronda;
        this.seleccionBuilder.listandoCompetidores();
        this.seleccionBuilder.clasificandoPorExperiencia();
        this.seleccionBuilder.obtenerVencedor(this.ronda);
    }

}
