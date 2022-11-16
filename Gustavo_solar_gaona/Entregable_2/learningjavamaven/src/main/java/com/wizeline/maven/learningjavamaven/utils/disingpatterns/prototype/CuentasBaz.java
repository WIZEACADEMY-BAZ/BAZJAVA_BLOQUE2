package com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype;

import java.util.logging.Logger;

public class CuentasBaz implements CuentasMolde, Cloneable{
    private static final Logger LOGGER = Logger.getLogger(CuentasBaz.class.getName());

    private final String tipoCuenta = "Cuenta nomina Baz";
    private double saldoInicial = 0.0;
    private double saldoMaximo = 10000.0;

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoMaximo() {
        return saldoMaximo;
    }

    public void setSaldoMaximo(double saldoMaximo) {
        this.saldoMaximo = saldoMaximo;
    }

    @Override
    public CuentasMolde generateCuentas(){
        try {
            CuentasMolde cuenta = (CuentasMolde)this.clone();
            LOGGER.info("Creando cuentas");
            System.out.println("Id de la cuenta: " + cuenta.hashCode());
            return cuenta;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
