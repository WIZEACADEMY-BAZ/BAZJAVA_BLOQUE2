package com.wizeline.gradle.learningjavagrade.abstractfactory;

import java.util.Scanner;

import com.wizeline.gradle.learningjavagrade.abstractfactory.factories.GUIFactory;
import com.wizeline.gradle.learningjavagrade.abstractfactory.factories.LinuxFactory;
import com.wizeline.gradle.learningjavagrade.abstractfactory.factories.MacOSFactory;
import com.wizeline.gradle.learningjavagrade.abstractfactory.factories.WindowsFactory;

public class Client {

    public static void main(String[] args) {
        Application app;
        GUIFactory factory;

        System.out.println("Choose an OS to render the app?");
        System.out.println("1.- MacOs");
        System.out.println("2.- Windows");
        System.out.println("3.- Linux");
        Scanner scan = new Scanner(System.in);
        int osOption = scan.nextInt();

        if (osOption == 1) {
            factory = new MacOSFactory();
        } else if (osOption == 2){
            factory = new WindowsFactory();
        } else {
            factory = new LinuxFactory();
        }

        app = new Application(factory);
        app.paint();
    }
}
