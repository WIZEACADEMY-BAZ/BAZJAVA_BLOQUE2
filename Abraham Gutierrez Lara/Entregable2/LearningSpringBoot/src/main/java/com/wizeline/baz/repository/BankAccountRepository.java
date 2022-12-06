package com.wizeline.baz.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wizeline.baz.model.BankAccountDTO;

public interface BankAccountRepository extends MongoRepository<BankAccountDTO, Long> {

}
