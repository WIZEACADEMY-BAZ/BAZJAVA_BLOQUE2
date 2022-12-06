package com.cursojava.proyecto.patterns.behavioral.chainofresponsibility;

import com.cursojava.proyecto.model.EntrenadorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ChainOfClasificadorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChainOfClasificadorHandler.class);
    protected ChainOfClasificadorHandler nextHandler = null;
    protected String classification;
    protected int puntaje;

    public ChainOfClasificadorHandler setNextHandler (ChainOfClasificadorHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    public EntrenadorDTO handler (EntrenadorDTO entrenadorDTO, int puntos) {

        if(puntos < this.puntaje){

            if(nextHandler==null){
                LOGGER.info("Lo sentimos... "+entrenadorDTO.getNombre() +" no pudo clasificar");
                return entrenadorDTO;
            }else {
                return nextHandler.handler(entrenadorDTO,puntos);
            }
        }else {
                LOGGER.info(entrenadorDTO.getNombre() +" a obtenido la clasificacion "+ this.classification);
                entrenadorDTO.setRango(this.classification);
                return entrenadorDTO;
        }
    }

}
