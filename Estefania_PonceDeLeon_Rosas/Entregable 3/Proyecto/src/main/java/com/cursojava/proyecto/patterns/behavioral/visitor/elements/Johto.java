package com.cursojava.proyecto.patterns.behavioral.visitor.elements;
import com.cursojava.proyecto.patterns.behavioral.visitor.Visitor;

public class Johto extends Region {

    public Johto() {
        super("Johto");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
