package com.baz.wizeline.learningspring.model;

public class EnSesion {

    private boolean activo;
    private static EnSesion enSesion;

    private EnSesion(boolean activo) {
        this.activo = activo;
        System.out.println("Se encuentra activo :" + this.activo);
    }

    public static EnSesion getSingletonInstance(boolean activo) {
        if (enSesion == null){
            enSesion = new EnSesion(true);
        }
        else{
            System.out.println("El usuario se encuentra activo ");
        }

        return enSesion;
    }
}
