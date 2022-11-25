package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.repository.Forma;

public class FabricaFormas {

    public Forma getShape(String shapeType){
        if(shapeType == null){
            return null;
        }
        if(shapeType.equalsIgnoreCase("CIRCULO")){
            return new Circulo();

        } else if(shapeType.equalsIgnoreCase("RECTANGULO")){
            return new Rectangulo();

        } else if(shapeType.equalsIgnoreCase("CUADRADO")){
            return new Cuadrado();
        }

        return null;
    }
}
