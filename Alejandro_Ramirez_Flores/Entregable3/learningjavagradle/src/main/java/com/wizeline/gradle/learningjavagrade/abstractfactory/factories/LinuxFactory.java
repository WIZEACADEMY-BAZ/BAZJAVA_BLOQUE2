package com.wizeline.gradle.learningjavagrade.abstractfactory.factories;

import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.Button;
import com.wizeline.gradle.learningjavagrade.abstractfactory.buttons.LinuxButton;

public class LinuxFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new LinuxButton();
    }
}
