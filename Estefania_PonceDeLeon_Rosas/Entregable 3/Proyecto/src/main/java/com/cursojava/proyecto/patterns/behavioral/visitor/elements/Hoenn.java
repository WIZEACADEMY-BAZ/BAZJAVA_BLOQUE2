package com.cursojava.proyecto.patterns.behavioral.visitor.elements;

import com.cursojava.proyecto.patterns.behavioral.visitor.Visitor;

public class Hoenn extends Region{

    public Hoenn(){
        super("Hoenn");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
