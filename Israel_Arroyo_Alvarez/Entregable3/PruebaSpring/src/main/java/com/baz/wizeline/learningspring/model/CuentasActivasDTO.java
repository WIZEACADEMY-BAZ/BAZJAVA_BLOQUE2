package com.baz.wizeline.learningspring.model;

public abstract class CuentasActivasDTO {
    CuentasActivasDTO nextActive = null;
    Integer activasFaltantes = 0;


    public CuentasActivasDTO setNextHandler (CuentasActivasDTO nextActive) {
        this.nextActive = nextActive;
        return this.nextActive;
    }

    public void handler (int cantActivas) {
        int activas = cantActivas / activasFaltantes;
        int sobranActivas = cantActivas % activasFaltantes;

        if (activas > 0) {
            System.out.printf("dispatched %d X %d = %d handled by %s \n",
                    activasFaltantes, activas, (activasFaltantes * activas), this.getClass().getSimpleName());
        }

        if (nextActive != null && sobranActivas > 0) {
            nextActive.handler(sobranActivas);
        }
    }


    public static class ConDosActivadas extends CuentasActivasDTO {
    public ConDosActivadas (Integer activasFaltantes) {
        this.activasFaltantes = activasFaltantes;
    }
}

    public static class ConUnaActivada extends CuentasActivasDTO {
    public ConUnaActivada (Integer activasFaltantes) {
        this.activasFaltantes = activasFaltantes;
    }
}

}


