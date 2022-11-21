package com.springboot.wizeline.app.springbootmaven.models.services.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.wizeline.app.springbootmaven.documents.Producto;
import reactor.core.publisher.Mono;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

    public Mono<Producto> findByNombre(String nombre);

    @Query("{ 'nombre': ?0 }")
    public Mono<Producto> obtenerPorNombre(String nombre);
}

