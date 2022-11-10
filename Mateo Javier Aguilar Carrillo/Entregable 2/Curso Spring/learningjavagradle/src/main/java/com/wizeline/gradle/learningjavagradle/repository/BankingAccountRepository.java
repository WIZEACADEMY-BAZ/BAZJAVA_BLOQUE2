package com.wizeline.gradle.learningjavagradle.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {

}
