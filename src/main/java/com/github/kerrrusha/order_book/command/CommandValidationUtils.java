package com.github.kerrrusha.order_book.command;

import static com.github.kerrrusha.order_book.command.Command.SEPARATOR;

public class CommandValidationUtils {
    public static boolean invalidCommandType(String commandStr, CommandType commandType) {
        if (commandType == CommandType.ANY) {
            if (commandStr.length() == 1)
                return firstParameterIsNotLetter(commandStr);
            return firstParameterIsNotLetter(commandStr) || secondCharIsNotAnSeparator(commandStr);
        }

        if (commandStr.length() == 1)
            return invalidSingleCase(commandStr, commandType);
        return invalidDefaultCase(commandStr, commandType);
    }
    public static boolean instructionIndexIsInvalid(int index, int instructionsCount) {
        return ! (0 <= index && index < instructionsCount);
    }

    private static boolean invalidSingleCase(String commandStr, CommandType commandType) {
        return !commandStr.equals(String.valueOf(commandType));
    }
    private static boolean invalidDefaultCase(String commandStr, CommandType commandType) {
        final String expected = String.valueOf(commandType.CODE) + SEPARATOR;
        String actual = getCommandTypeWithSeparator(commandStr);

        return !expected.equals(actual);
    }
    private static char getCommandType(String commandStr) {
        return commandStr.charAt(0);
    }
    private static String getCommandTypeWithSeparator(String commandStr) {
        return commandStr.substring(0, 2);
    }
    private static boolean firstParameterIsNotLetter(String commandStr) {
        return !Character.isLetter(getCommandType(commandStr));
    }
    private static boolean secondCharIsNotAnSeparator(String commandStr) {
        return commandStr.charAt(1) != SEPARATOR;
    }
}
