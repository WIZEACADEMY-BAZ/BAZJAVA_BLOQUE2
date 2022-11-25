package com.wizeline.learningspring.patrones.prototype;

public class MiG28Plane implements PlaneMold, Cloneable {

    @Override
    public void createPlane() {
        try {
            Object obj = this.clone();
            System.out.println("Pintando el avion de color azul");
            System.out.println("Created plane object with id: " + obj.hashCode());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}