

package com.wizeline.maven.learningjavagmaven.repository;

        import org.springframework.data.mongodb.repository.MongoRepository;
        import org.springframework.stereotype.Repository;

        import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;

/**
 * Class Description goes here.
 * Created by enrique.gutierrez on 27/09/22
 */
@Repository
public interface BankingAccountRepository extends MongoRepository<BankAccountModel, Long> {
}