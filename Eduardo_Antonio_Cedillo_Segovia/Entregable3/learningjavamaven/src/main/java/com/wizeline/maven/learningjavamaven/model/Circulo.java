package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.repository.Forma;

public class Circulo implements Forma {

    @Override
    public void dibujar(){
        System.out.println("El circulo es color amarillo");
    }
}
