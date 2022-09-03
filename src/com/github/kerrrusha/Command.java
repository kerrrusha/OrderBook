package com.github.kerrrusha;

public class Command {
    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public int instructionsCount() {
        return command.length();
    }
}
