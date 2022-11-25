package com.wizeline.maven.learningjavamaven.model;


import org.springframework.stereotype.Repository;

@Repository
public class Saving implements Account {
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
            System.out.print("Ya no tienes efectivo" + efectivo);
        } else{
            System.out.println("Tienes efectiovo" + efectivo);
        }
    }

    public int getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(int efectivo) {
        this.efectivo = efectivo;
    }


}
