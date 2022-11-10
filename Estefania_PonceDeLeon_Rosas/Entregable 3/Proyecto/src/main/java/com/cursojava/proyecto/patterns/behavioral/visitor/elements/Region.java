package com.cursojava.proyecto.patterns.behavioral.visitor.elements;
import com.cursojava.proyecto.patterns.behavioral.visitor.Visitor;

public abstract class Region {

    public String nombre;

    public Region(String nombre) {
        this.nombre = nombre;
    }

    public abstract void accept(Visitor v);
}
