package com.wizeline.maven.learningjavagmaven.Observer;

import com.wizeline.maven.learningjavagmaven.Observer.Observadores.Observador;
import com.wizeline.maven.learningjavagmaven.Observer.Observadores.SeguObservador;

import java.util.ArrayList;
import  java.util.List;
public class Observable {

    private List<Observador> observers=new ArrayList<>();
    private int number;

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        notificaaAtodosObservadores();
    }

    public void attach(Observador observer){
        observers.add(observer);
    }

    public void notificaaAtodosObservadores(){
        for (Observador observador : observers) {
            observador.update(this.number);
        }
    }
}
