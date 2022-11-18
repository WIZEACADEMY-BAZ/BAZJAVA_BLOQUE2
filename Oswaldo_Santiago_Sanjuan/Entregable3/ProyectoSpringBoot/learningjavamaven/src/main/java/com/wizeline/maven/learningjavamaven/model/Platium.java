package com.wizeline.maven.learningjavamaven.model;

import org.springframework.stereotype.Repository;

@Repository
public class Platium implements Account {
    private  int efectivo;
    private int accountNumber;



    public int getAccountNumber() {
        return accountNumber;
    }
    @Override
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void tieneFondos(){

        if (efectivo > 0)  {
            System.out.println("Tienes efectiovo" + efectivo);
        } else{
            System.out.print("Ya no tienes efectivo" + efectivo);
        }
    }



    public int getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(int efectivo) {
        this.efectivo = efectivo;
    }
}
