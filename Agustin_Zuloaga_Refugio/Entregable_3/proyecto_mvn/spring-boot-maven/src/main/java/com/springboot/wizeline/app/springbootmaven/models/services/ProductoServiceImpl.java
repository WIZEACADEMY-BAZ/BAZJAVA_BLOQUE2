package com.springboot.wizeline.app.springbootmaven.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.wizeline.app.springbootmaven.documents.Categoria;
import com.springboot.wizeline.app.springbootmaven.documents.Producto;
import com.springboot.wizeline.app.springbootmaven.models.services.dao.CategoriaDao;
import com.springboot.wizeline.app.springbootmaven.models.services.dao.ProductoDao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	private ProductoDao dao;

	@Autowired
	private CategoriaDao categoriaDao;

	@Override
	public Flux<Producto> findAll() {
		return dao.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return dao.save(producto);
	}

	@Override
	public Mono<Void> delete(Producto producto) {
		return dao.delete(producto);
	}

	@Override
	public Flux<Producto> findAllConNombreUpperCase() {
		return dao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
	}

	@Override
	public Flux<Producto> findAllConNombreUpperCaseRepeat() {
		return findAllConNombreUpperCase().repeat(5000);
	}

	@Override
	public Flux<Categoria> findAllCategoria() {
		return categoriaDao.findAll();
	}

	@Override
	public Mono<Categoria> findCategoriaById(String id) {
		return categoriaDao.findById(id);
	}

	@Override
	public Mono<Categoria> saveCategoria(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

@Override
public Mono<Producto> findByNombre(String nombre) {
	return dao.obtenerPorNombre(nombre);
}

@Override
public Mono<Categoria> findCategoriaByNombre(String nombre) {
	return categoriaDao.findByNombre(nombre);
	//		findByNombre(nombre);
}


}
