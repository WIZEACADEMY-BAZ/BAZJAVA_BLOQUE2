package com.wizeline.maven.learningjavamaven.repository;


import com.wizeline.maven.learningjavamaven.elements.Element;
import com.wizeline.maven.learningjavamaven.elements.JsonElement;
import com.wizeline.maven.learningjavamaven.elements.XmlElement;

import java.util.List;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);

    void visit(List<Element> elements);
}