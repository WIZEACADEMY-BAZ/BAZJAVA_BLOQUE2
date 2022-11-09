package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Revisión: Creación de enlace con MongoDB usando Spring Data JPA
@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long> {
}
