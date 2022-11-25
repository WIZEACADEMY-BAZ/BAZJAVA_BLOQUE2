package baz.practice.wizeline.learningjavamaven.repository;

import baz.practice.wizeline.learningjavamaven.model.BankAccountDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountDTO, Long>{
}
