package com.wizeline.maven.learningjavagmaven.FactoryMeth;

public class PruebaFactoryMeth {
    public static void PruebaFactoryMeth(){
         CuentaFactory cuentaFactory = new CuentaFactory() ;
         Cuenta cuenta1= cuentaFactory.CuentaFactory("France");

        System.out.println(cuenta1.mensaje());

    }

}
