package com.Wizeline.maven.learningjavamaven.repository;

import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}