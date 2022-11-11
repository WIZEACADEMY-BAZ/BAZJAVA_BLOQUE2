package com.wizeline.gradle.learningjavagradle.iterator;

import java.util.*;
import java.util.logging.Logger;


public class ProductosIterator {
	private static final Logger LOGGER = Logger.getLogger("ProductosIterator.class");
ArrayList<Producto> productos = new ArrayList<>();
{
productos.add(new Producto("1", "Tornillos"));
productos.add(new Producto("2", "Tuercas"));
productos.add(new Producto("3", "Llaves"));
LOGGER.info(impProductos(productos));
}

private static String impProductos(ArrayList<Producto>productos) {
	String lista="";
	Iterator <Producto>iterator = productos.iterator();
	
	while(iterator.hasNext()) {
		lista += iterator.next().toString();
	}
	return lista;
}

}
