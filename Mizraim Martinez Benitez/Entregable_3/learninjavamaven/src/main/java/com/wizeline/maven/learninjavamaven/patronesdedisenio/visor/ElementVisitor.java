package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor;

import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.Element;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.JsonElement;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.XmlElement;

import java.util.List;

public class ElementVisitor implements Visitor {

    @Override
    public void visit(XmlElement xe) {
        System.out.println(
                "processing an XML element with uuid: " + xe.uuid);
    }

    @Override
    public void visit(JsonElement je) {
        System.out.println(
                "processing a JSON element with uuid: " + je.uuid);
    }

    @Override
    public void visit(List<Element> elements) {
        for (Element element : elements) {
            element.accept(this);
        }
    }

}
