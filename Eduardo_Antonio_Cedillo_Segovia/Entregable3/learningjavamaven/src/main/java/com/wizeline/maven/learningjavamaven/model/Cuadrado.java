package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.repository.Forma;

public class Cuadrado implements Forma {

    @Override
    public void dibujar(){
        System.out.println("El cuadrado es color azul");
    }
}
