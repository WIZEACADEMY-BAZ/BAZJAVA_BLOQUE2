package com.wizeline.maven.learninjavamaven.repository;

import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {

}
