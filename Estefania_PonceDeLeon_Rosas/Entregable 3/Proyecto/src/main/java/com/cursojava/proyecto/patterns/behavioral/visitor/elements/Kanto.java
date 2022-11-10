package com.cursojava.proyecto.patterns.behavioral.visitor.elements;

import com.cursojava.proyecto.patterns.behavioral.visitor.Visitor;

public class Kanto extends Region {

    public Kanto() {
        super("Kanto");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
