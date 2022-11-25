package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements;

import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.Visitor;

public abstract class Element {

    public String uuid;

    public Element(String uuid) {
        this.uuid = uuid;
    }

    public abstract void accept(Visitor visitor);
}
