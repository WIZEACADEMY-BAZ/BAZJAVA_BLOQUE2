package com.cursojava.proyecto.patterns.behavioral.observer;

import com.cursojava.proyecto.model.EntrenadorDTO;
import com.cursojava.proyecto.patterns.behavioral.observer.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<Observer> observers = new ArrayList<>();
    private EntrenadorDTO entrenadorDTO;

    public EntrenadorDTO getEntrenadorDTO() {
        return entrenadorDTO;
    }

    public void setEntrenadorDTO(EntrenadorDTO entrenadorDTO) {
        this.entrenadorDTO = entrenadorDTO;
        notifyAllObserversUpdate();
    }
    public void removeEntrenadorDTO(EntrenadorDTO entrenadorDTO) {
        this.entrenadorDTO = entrenadorDTO;
        notifyAllObserversRemove();
    }

    public void anunciarGanador(EntrenadorDTO entrenadorDTO) {
        this.entrenadorDTO = entrenadorDTO;
        notifyAllObserversGanador();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObserversUpdate(){
        for (Observer observer : observers) {
            observer.update(this.entrenadorDTO);
        }
    }

    public void notifyAllObserversRemove(){
        for (Observer observer : observers) {
            observer.retire(this.entrenadorDTO);
        }
    }
    public void notifyAllObserversGanador(){
        for (Observer observer : observers) {
            observer.anunciar(this.entrenadorDTO);
        }
    }
}
