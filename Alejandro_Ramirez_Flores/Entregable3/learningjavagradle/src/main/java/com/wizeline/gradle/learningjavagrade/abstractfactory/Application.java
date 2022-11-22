package com.wizeline.gradle.learningjavagrade.abstractfactory;

import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.Button;
import com.wizeline.gradle.learningjavagrade.abstractfactory.factories.GUIFactory;

public class Application {

    private Button button;

    public Application(GUIFactory factory) {
        button = factory.createButton();
    }

    public void paint() {
        button.paint();
    }
}
