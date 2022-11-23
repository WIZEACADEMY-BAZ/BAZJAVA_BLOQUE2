package com.wizeline.maven.learningjavamaven.elements;

import com.wizeline.maven.learningjavamaven.repository.Visitor;

public abstract class Element {

    public String uuid;

    public Element(String uuid) {
        this.uuid = uuid;
    }

    public abstract void accept(Visitor v);
}
