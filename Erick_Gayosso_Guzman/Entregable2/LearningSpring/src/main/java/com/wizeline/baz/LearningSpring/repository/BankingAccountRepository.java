package com.wizeline.baz.LearningSpring.repository;

import com.wizeline.baz.LearningSpring.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
