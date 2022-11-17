package com.wizeline.maven.learningjavamaven.utils.disingpatterns.prototype;

public interface CuentasMolde {
    String getTipoCuenta();
    double getSaldoInicial() ;
    void setSaldoInicial(double saldoInicial);
    double getSaldoMaximo();
    void setSaldoMaximo(double saldoMaximo);
    CuentasMolde generateCuentas() throws CloneNotSupportedException;
}
