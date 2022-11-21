package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.repository.Forma;

public class Rectangulo implements Forma {

    @Override
    public void dibujar(){
        System.out.println("El rectangulo es color azul");
    }
}
