package com.cursojava.proyecto.patterns.behavioral.visitor;

import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Hoenn;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Johto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Kanto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Region;

import java.util.List;

public interface Visitor {

    void visit(Kanto ka);

    void visit(Johto jo);

    void visit(Hoenn ho);

    void visit(List<Region> elements);
}
