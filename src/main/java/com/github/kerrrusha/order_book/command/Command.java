package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.StringUtils;

public class Command {
    protected static final char SEPARATOR = ',';

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
        if ( !instructionIndexIsCorrect(positionIndex) )
            throw new InstructionIndexOutOfBoundsException("Instruction index was out of bounds.");
        int startIndex = 0;
        for (int i = 0; i < positionIndex; i++) {
            startIndex = command.substring(startIndex).indexOf(SEPARATOR) + 1;
        }
        int endIndex = command.substring(startIndex).indexOf(SEPARATOR);
        endIndex = endIndex == -1 ? command.length() : endIndex + startIndex;
        return command.substring(startIndex, endIndex);
    }

    private boolean instructionIndexIsCorrect(int index) {
        return 0 <= index && index < instructionsCount();
    }
}
