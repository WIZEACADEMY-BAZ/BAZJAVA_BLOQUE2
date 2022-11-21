package com.wizeline.maven.learningjavagmaven.singleton;

public class PruebaSingletonThread {

    public static void PruebaSingleton() {
        System.out.println("validando que se uso solo una vez el singleton, si aparece 2 veces CORRECTO es valida si aparece CORRECTO ERRORNEO se uso 2 veces "+ "\n\n" +
                "RESULT:" + "\n");
        Singleton singleton = Singleton.getInstance("CORRECTO");
        Singleton anotherSingleton = Singleton.getInstance("ERRONEO");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);

    }
}
