package com.wizeline.learningspring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wizeline.learningspring.model.BankAccountDTO;

import java.util.Optional;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
    Optional<BankAccountDTO> findByAccountNumber(Long accountNumber);
}
