package baz.practice.wizeline.learningjavamaven.factory;

import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;

public class CuentaNomina implements Cuenta{
    @Override
    public String mensaje() {
        return "Usted esta creando una cuenta de tipo 'Nomina'";
    }
}
