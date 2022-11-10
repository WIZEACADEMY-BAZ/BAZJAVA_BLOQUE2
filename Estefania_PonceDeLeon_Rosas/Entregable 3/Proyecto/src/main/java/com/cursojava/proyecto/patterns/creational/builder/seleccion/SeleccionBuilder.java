package com.cursojava.proyecto.patterns.creational.builder.seleccion;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.patterns.creational.builder.entrenadores.Enfrentamiento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeleccionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(SeleccionBuilder.class);
    private EntrenadorDTO vencedor =null;

    private Enfrentamiento enfrentamiento;

    public Enfrentamiento getEnfrentamiento() {
        return enfrentamiento;
    }

    public SeleccionBuilder (){
        enfrentamiento=new Enfrentamiento();
    }

    public abstract void addCompetidores();

    public void listandoCompetidores(){
        LOGGER.info("Listando competidores...");
    }

    public void clasificandoPorExperiencia(){
        LOGGER.info("Clasificando por experiencia...");
    }

    public EntrenadorDTO obtenerVencedor(String ronda){
        LOGGER.info("Iniciando ronda "+ronda);
        this.addCompetidores();
        this.enfrentamiento.verCompeticion();
        this.vencedor=this.enfrentamiento.getVencedor();
        return this.vencedor;
    }

}
