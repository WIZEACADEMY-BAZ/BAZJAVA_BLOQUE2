package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor;

import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.Element;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.JsonElement;
import com.wizeline.maven.learninjavamaven.patronesdedisenio.visor.elements.XmlElement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {

    public static void main(String[] args) {
        List<Element> elements = new ArrayList<>();
        elements.add(new JsonElement(generateUuid()));
        elements.add(new JsonElement(generateUuid()));
        elements.add(new XmlElement(generateUuid()));
    }

    private static String generateUuid(){
        return UUID.randomUUID().toString();
    }
}
