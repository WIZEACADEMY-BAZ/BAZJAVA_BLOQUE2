package com.wizeline.maven.learningjava.Learning.service;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface ValidarCodigo<T> {

    public static final List<String> listaCodigos = Arrays.asList("23505", "53919", "59590");

    boolean validarCodigo(T domicilio);
}
