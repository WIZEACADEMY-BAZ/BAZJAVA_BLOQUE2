package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements;


import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.Visitor;

public class XmlElement extends Element {

    public XmlElement(String uuid){
        super(uuid);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
