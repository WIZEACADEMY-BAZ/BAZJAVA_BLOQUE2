package com.springboot.wizeline.app.springbootmaven.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.wizeline.app.springbootmaven.documents.Categoria;

public interface CategoriaDao extends ReactiveMongoRepository<Categoria, String>{

}
