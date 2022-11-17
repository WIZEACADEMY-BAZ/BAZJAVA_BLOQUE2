package com.wizeline.maven.learningjavamaven.utils.disingpatterns.singleton;

public class InstanseSingleton {

    private static InstanseSingleton instance;
    private double randomNumber = 0.0;

    private InstanseSingleton(){
        this.randomNumber = Math.random();
    }

    private synchronized static void createInstance() {
        if (instance == null) {
            InstanseSingleton.instance = new InstanseSingleton();
        }
    }

    public static InstanseSingleton getInstance() {
        createInstance();
        return instance;
    }

    public void randomNumber(){
        System.out.println("Clase singleton: " + this.randomNumber);
    }

}
