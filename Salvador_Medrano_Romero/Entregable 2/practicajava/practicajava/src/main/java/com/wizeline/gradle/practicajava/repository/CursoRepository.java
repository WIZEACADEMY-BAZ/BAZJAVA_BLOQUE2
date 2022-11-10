package com.wizeline.gradle.practicajava.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;

@Repository
public interface CursoRepository extends MongoRepository<EstudianteDTO, Long>{

}
