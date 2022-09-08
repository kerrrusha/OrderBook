package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.StringUtils;

import java.util.Objects;

public class Command {
    protected static final char SEPARATOR = ',';
    protected static final String INSTRUCTION_INDEX_OUT_OF_BOUNDS = "Instruction index was out of bounds.";

    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public int instructionsCount() {
        if (command.trim().length() == 0)
            return 0;
        return StringUtils.countMatches(command, String.valueOf(SEPARATOR)) + 1;
    }
    public String getInstructionAtIndex(int positionIndex) {
        if (CommandValidationUtils.instructionIndexIsInvalid(positionIndex, instructionsCount()))
            throw new InstructionIndexOutOfBoundsException(INSTRUCTION_INDEX_OUT_OF_BOUNDS);
        int startIndex = 0;
        for (int i = 0; i < positionIndex; i++) {
            startIndex += command.substring(startIndex).indexOf(SEPARATOR) + 1;
        }
        int endIndex = command.substring(startIndex).indexOf(SEPARATOR);
        endIndex = endIndex == -1 ? command.length() : endIndex + startIndex;
        return command.substring(startIndex, endIndex);
    }
    @Override
    public String toString() {
        return "[" + command + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Command)) {
            return false;
        }

        Command other = (Command) o;

        return Objects.equals(this.command, other.command);
    }
}
