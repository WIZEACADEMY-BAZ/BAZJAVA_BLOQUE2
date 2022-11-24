package com.wizeline.gradle.learningjavagrade.abstractfactory.factories;

import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.Button;
import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.MacOsButton;

public class MacOSFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new MacOsButton();
    }
}
