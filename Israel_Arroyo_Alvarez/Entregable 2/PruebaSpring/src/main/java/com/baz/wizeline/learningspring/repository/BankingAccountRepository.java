package com.baz.wizeline.learningspring.repository;

import com.baz.wizeline.learningspring.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
