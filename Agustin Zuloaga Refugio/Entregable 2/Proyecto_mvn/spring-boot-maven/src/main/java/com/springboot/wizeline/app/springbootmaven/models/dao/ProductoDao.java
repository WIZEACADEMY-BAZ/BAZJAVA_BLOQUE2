package com.springboot.wizeline.app.springbootmaven.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.springboot.wizeline.app.springbootmaven.documents.Producto;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

}
