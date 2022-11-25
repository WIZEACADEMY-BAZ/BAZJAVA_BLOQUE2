package com.wizeline.maven.learninjavamaven.patronesdedisenio.singleton;

public class DemoSingleThread {

    public static void main(String[] args) {
        System.out.println("Si ve el mismo valor, entonces se reutilizó singleton (¡si!)" + "\n" +
                "Si ves valores diferentes, entonces se crearon 2 singletons (booo!!)" + "\n\n" +
                "RESULT:" + "\n");
        Singleton singleton = Singleton.getInstance("FOO");
        Singleton anotherSingleton = Singleton.getInstance("BAR");
        System.out.println(singleton.value);
        System.out.println(anotherSingleton.value);
    }
}
