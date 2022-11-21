package com.wizeline.learningspring.patrones.prototype;

import java.util.Scanner;

public class Cliente {
    public static void main (String[] args) throws CloneNotSupportedException {
        System.out.println("How many Planes you want to create based on Mold / Prototype ?");
        Scanner scan = new Scanner(System.in);
        int quantity = scan.nextInt();

        PlaneMold planeMold = new MiG28Plane();

        for(int i=0;i<quantity;i++){
            planeMold.createPlane();
        }

    }
}
