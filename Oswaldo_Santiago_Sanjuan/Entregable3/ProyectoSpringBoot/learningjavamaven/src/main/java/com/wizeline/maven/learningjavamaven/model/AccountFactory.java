package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import org.springframework.stereotype.Repository;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.*;

@Repository
public class AccountFactory {

    public Account getcuenta (AccountType accountType){

        if (NOMINA.equals(accountType)){
            return new Nominating();
        }

        if (PLATINUM.equals(accountType)){
            return new Platium();
        }

        if (AHORRO.equals(accountType)){
            return new saving();
        }

        return null;
    }


}
