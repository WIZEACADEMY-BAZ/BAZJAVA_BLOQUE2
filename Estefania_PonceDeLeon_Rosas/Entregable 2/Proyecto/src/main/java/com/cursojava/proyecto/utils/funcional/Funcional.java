package com.cursojava.proyecto.utils.funcional;

public interface Funcional <T,U,V, R>{
    R apply(T t, U u, V v);
}
