package com.wizeline.maven.learningjavagmaven.Observer.Observadores;

public class SextoObservador implements Observador{

    @Override
    public void update(int number){
        System.out.println("Sexto visor" + Integer.toHexString( number).toUpperCase());
    }

}
