package com.wizeline.baz.LearningSpring.repository;

import com.wizeline.baz.LearningSpring.model.TeamsDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperationsWorldCupRepository extends MongoRepository<TeamsDTO, String> {
}
