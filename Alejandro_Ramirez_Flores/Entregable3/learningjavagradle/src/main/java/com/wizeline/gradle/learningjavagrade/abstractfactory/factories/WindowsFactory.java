package com.wizeline.gradle.learningjavagrade.abstractfactory.factories;

import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.Button;
import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.WindowsButton;

public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}
