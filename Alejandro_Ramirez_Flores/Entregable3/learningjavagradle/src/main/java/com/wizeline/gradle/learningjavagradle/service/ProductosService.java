package com.wizeline.gradle.learningjavagradle.service;

import java.util.*;
import java.util.logging.Logger;

import com.wizeline.gradle.learningjavagradle.iterator.Producto;

public class ProductosService{
	private static final Logger LOGGER = Logger.getLogger("ProductosIterator.class");


public static String impProductos(){
	
	 ArrayList<Producto> productos = new ArrayList<>();
	productos.add(new Producto("1", "Tornillos"));
	productos.add(new Producto("2", "Tuercas"));
	productos.add(new Producto("3", "Llaves"));
	
	
	String lista="";
	
	Iterator<Producto> iterator = productos.iterator();
	
	while(iterator.hasNext()) {
		lista += iterator.next().toString();
	}
	return lista;
}


}
