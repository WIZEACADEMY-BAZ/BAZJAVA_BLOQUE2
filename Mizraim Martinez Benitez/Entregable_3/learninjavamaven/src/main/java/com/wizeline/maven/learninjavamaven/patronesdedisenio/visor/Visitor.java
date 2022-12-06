package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor;

import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.Element;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.JsonElement;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.XmlElement;

import java.util.List;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);

    void visit(List<Element> elements);
}
