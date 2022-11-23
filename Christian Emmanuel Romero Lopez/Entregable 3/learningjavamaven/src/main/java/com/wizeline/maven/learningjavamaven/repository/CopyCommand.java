package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.patterns.Receiver;

public class CopyCommand implements Command {

    private Receiver receiver;

    public CopyCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.copy();
    }
}
