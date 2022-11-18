package com.wizeline.maven.learningjavamaven.model;

import org.springframework.stereotype.Repository;

@Repository
public class Cuenta {

    public Account getcuenta (String accountNumber ){

        if (accountNumber.equalsIgnoreCase("nomina")){
            return new Nominating();
        }

        if (accountNumber.equalsIgnoreCase("platium")){
            return new Platium();
        }

        if (accountNumber.equalsIgnoreCase("ahorro")){
            return new saving();
        }

        return null;
    }


}
