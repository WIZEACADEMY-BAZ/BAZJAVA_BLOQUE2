package com.wizeline.maven.learningjavagmaven.Observer;

import com.wizeline.maven.learningjavagmaven.Observer.Observadores.SeguObservador;
import com.wizeline.maven.learningjavagmaven.Observer.Observadores.SextoObservador;

public class Cliente {

    public static void  clienteObservador (){
        Observable observable=new Observable();

        observable.attach(new SeguObservador());
        observable.attach(new SextoObservador());

        System.out.println("------ primer cambio  de estado: 15");
        observable.setNumber(15);
        System.out.println("====================================");
        System.out.println("------ segundo cambio  de estado: 7");
        observable.setNumber(7);
    }

}
