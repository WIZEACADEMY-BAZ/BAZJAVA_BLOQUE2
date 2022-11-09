package com.wizeline.gradle.learningjavagradle.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;

@Repository
public class BankingAccountNominaRepositoryImpl implements BankingAccountNominaRepository{
	@Autowired
	MongoTemplate mt;
	
	@Override
	public Optional<BankAccountNomina> obtenerCuenta(String user) {
		  Query query = new Query();
	      query.addCriteria(Criteria.where("user").is(user));
        BankAccountNomina cnomina= mt.findOne(query, BankAccountNomina.class, "BankAccountNomina");
        /*
         * Uso de Optional
         */
		Optional<BankAccountNomina> response = Optional.ofNullable(cnomina);
		return response;
	}

	@Override
	public boolean insertaCuenta(BankAccountNomina request) {
		try {
			mt.save(request,"BankAccountNomina");
			return true;
		}catch(Exception e) {
			return false;
		}		
	}

	@Override
	public boolean updateCuenta(BankAccountNomina request) {
		try {
			Query query = new Query();
		    query.addCriteria(Criteria.where("accountNumber").is(request.getAccountNumber()));
			mt.findAndReplace(query,request);
			return true;
		}catch(Exception e) {
			return false;
		}	
	}

	@Override
	public boolean deleteCuenta(Long accountNumber) {
		try {
		Query query = new Query();
	    query.addCriteria(Criteria.where("accountNumber").is(accountNumber));
	    mt.remove(query, BankAccountNomina.class);
	    return true;
		}catch(Exception e) {
			return false;
		}
	}
}
