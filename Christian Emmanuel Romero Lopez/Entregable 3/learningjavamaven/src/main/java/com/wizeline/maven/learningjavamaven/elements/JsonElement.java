package com.wizeline.maven.learningjavamaven.elements;

import com.wizeline.maven.learningjavamaven.repository.Visitor;

public class JsonElement extends Element {

    public JsonElement(String uuid) {
        super(uuid);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
