package com.cursojava.proyecto.patterns.behavioral.observer.observers;

import com.cursojava.proyecto.model.EntrenadorDTO;

public interface Observer {

    void update(EntrenadorDTO entrenadorDTO);
    void retire(EntrenadorDTO entrenadorDTO);

    void anunciar(EntrenadorDTO entrenadorDTO);
}
