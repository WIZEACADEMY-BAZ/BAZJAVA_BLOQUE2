package com.cursojava.proyecto.patterns.behavioral.visitor;

import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Hoenn;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Johto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Kanto;
import com.cursojava.proyecto.patterns.behavioral.visitor.elements.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ElementVisitor implements Visitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElementVisitor.class);
    @Override
    public void visit(Kanto ka) {
        LOGGER.info("Visitando...  " + ka.nombre);
    }

    @Override
    public void visit(Johto jo) {
        LOGGER.info("Visitando...  " + jo.nombre);
    }

    @Override
    public void visit(Hoenn ho) {
        LOGGER.info("Visitando...  " + ho.nombre);
    }

    @Override
    public void visit(List<Region> elements) {
        for (Region element : elements) {
            element.accept(this);
        }
    }
}
