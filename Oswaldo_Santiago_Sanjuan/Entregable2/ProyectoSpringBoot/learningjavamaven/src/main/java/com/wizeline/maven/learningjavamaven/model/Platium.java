package com.wizeline.maven.learningjavamaven.model;

import org.springframework.stereotype.Repository;

@Repository
public class Platium implements  accounts{

    @Override
    public void  getAccountNumber(){
        System.out.println("Entoy en la cuenta platium");

    }
}
