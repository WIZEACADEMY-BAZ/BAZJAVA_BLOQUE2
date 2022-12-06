package com.wizeline.maven.learninjavamaven.model;

import java.util.Objects;

public class Persona<T>{
    private T t;

    private String nombre;

    public void set(T t) { this.t = t; }
    public T get() { return t; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona<?> persona = (Persona<?>) o;
        return Objects.equals(t, persona.t) && Objects.equals(nombre, persona.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, nombre);
    }

    @Override
    public String toString() {
        return "Persona{" +
                "t=" + t +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
