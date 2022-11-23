package com.wizeline.maven.learningjavamaven.patterns;

import com.wizeline.maven.learningjavamaven.repository.Command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    private final List<Command> operations
            = new ArrayList<>();

    public void executeOperation(Command command) {
        operations.add(command);
        command.execute();
    }
}
