package com.cursojava.proyecto.patterns.behavioral.command;

public class CatchCommand implements Command {

    private Receiver receiver;

    public CatchCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void atraparPokemon() {
        receiver.lanzarPokeball();
    }
}
