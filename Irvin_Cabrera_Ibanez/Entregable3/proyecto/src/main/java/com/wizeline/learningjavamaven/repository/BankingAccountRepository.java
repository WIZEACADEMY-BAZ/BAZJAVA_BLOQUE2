package com.wizeline.learningjavamaven.repository;

import com.wizeline.learningjavamaven.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
