package com.wizeline.maven.learningjavamaven.patterns;

import com.wizeline.maven.learningjavamaven.controller.UserController;
import com.wizeline.maven.learningjavamaven.elements.Element;
import com.wizeline.maven.learningjavamaven.elements.JsonElement;
import com.wizeline.maven.learningjavamaven.elements.XmlElement;
import com.wizeline.maven.learningjavamaven.repository.Visitor;

import java.util.List;
import java.util.logging.Logger;

public class ElementVisitor implements Visitor {

    private static final Logger LOGGER = Logger.getLogger(ElementVisitor.class.getName());

    @Override
    public void visit(XmlElement xe) {
        LOGGER.info( "Processing an XML element with Cliente: " + xe.uuid );
    }

    @Override
    public void visit(JsonElement je) {
        LOGGER.info( "Processing a JSON element with uuid: " + je.uuid );
    }

    @Override
    public void visit(List<Element> elements) {
        for (Element element : elements) {
            element.accept(this);
        }
    }
}
