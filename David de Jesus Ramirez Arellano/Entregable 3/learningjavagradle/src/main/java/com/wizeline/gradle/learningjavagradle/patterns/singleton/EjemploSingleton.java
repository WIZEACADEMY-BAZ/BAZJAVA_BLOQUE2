package com.wizeline.gradle.learningjavagradle.patterns.singleton;

public class EjemploSingleton {
	
    private static EjemploSingleton instance;
    private String dato;

    private EjemploSingleton(String dato){
        this.dato = dato;
    }

    public static EjemploSingleton getInstance() {
    	  if (instance == null) {
              instance = new EjemploSingleton("Instancia de Singleton");
          }
          return instance;
    }

	public String getDato() {
		return dato;
	}

}
