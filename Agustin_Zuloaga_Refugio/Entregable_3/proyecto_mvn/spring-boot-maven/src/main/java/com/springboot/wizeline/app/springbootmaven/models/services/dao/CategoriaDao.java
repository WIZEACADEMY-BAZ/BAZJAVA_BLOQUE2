package com.springboot.wizeline.app.springbootmaven.models.services.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.wizeline.app.springbootmaven.documents.Categoria;
import reactor.core.publisher.Mono;

public interface CategoriaDao extends ReactiveMongoRepository<Categoria, String>{

    Mono<Categoria> findByNombre(String nombre);


}
