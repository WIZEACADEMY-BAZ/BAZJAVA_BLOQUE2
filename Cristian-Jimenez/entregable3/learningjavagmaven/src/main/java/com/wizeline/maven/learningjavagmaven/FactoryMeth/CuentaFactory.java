package com.wizeline.maven.learningjavagmaven.FactoryMeth;

public class CuentaFactory {
    public  Cuenta CuentaFactory(String channe1){
        if (channe1 == null || channe1.isEmpty())
            return null;
        switch (channe1){
            case "France":
                return new France();
            case "Mexico":
                return new Mexico();
            case "United States":
                return new UnitedStates();
            default:
                throw new IllegalArgumentException("Pais desconocido" + channe1);
        }
    }

}
