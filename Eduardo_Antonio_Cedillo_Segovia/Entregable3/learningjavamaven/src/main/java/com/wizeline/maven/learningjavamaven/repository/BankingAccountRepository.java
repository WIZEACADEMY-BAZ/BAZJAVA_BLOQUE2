package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


//Aqui se emplea Herencia con la palabra extends a la clase padre
@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long>{
}
