package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.StringUtils;

public class Command {
    private final String SEPARATOR = ",";

    private final String command;

    public Command(String command) {
        this.command = command;
    }

    public int instructionsCount() {
        return StringUtils.countMatches(command, SEPARATOR) + 1;
    }
    public String getInstructionAtIndex(int positionIndex) {
        if (positionIndex > instructionsCount())
            throw new InstructionIndexOutOfBoundsException("Instruction index was out of bounds.");
//        if (positionIndex == 0) {
//            int endIndex = command.indexOf(SEPARATOR);
//            return command.substring(0, endIndex);
//        }
        int startIndex = 0;
        int count = 0;
        while (count != positionIndex) {
            startIndex = command.substring(startIndex).indexOf(SEPARATOR);
            count++;
        }
        int endIndex = command.substring(startIndex).indexOf(SEPARATOR);
        return command.substring(startIndex, endIndex);
    }
}
