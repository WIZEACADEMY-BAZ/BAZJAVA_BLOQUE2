package com.wizeline.maven.learningjavagmaven.Observer.Observadores;


public class SeguObservador implements Observador {
    @Override
    public  void  update(int number) {
        System.out.println("segundo observador:" + Integer.toBinaryString(number));
    }
}
