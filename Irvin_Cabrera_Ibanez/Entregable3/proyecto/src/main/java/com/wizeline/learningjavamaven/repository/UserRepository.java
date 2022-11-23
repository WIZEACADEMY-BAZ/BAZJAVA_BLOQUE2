package com.wizeline.learningjavamaven.repository;

import com.wizeline.learningjavamaven.model.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDTO, String> {
}
