package com.wizeline.gradle.learningjavagradle.patterns.singleton;

import java.util.logging.Logger;


public class SingletonImpl {
    private static final Logger LOGGER = Logger.getLogger(SingletonImpl.class.getName());
    
	public static void main(String [] args) {
		EjemploSingleton singleton;
		singleton = EjemploSingleton.getInstance();
		LOGGER.info(singleton.getDato());
	}
}
