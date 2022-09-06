package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.StringUtils;

import static com.github.kerrrusha.order_book.command.Command.SEPARATOR;

public class CommandValidationUtils {
    public static boolean invalidCommandType(String commandStr, CommandType commandType) {
        if (StringUtils.stringIsInvalid(commandStr))
            return true;
        if (commandStr.length() == 1)
            return invalidSingleCase(commandStr, commandType);
        return invalidDefaultCase(commandStr, commandType);
    }
    public static boolean instructionIndexIsInvalid(int index, int instructionsCount) {
        return ! (0 <= index && index < instructionsCount);
    }

    private static boolean invalidSingleCase(String commandStr, CommandType commandType) {
        return commandStr.equals(String.valueOf(commandType));
    }
    private static boolean invalidDefaultCase(String commandStr, CommandType commandType) {
        return getCommandTypeWithSeparator(commandStr).equals(String.valueOf(commandType) + SEPARATOR);
    }
    private static String getCommandTypeWithSeparator(String commandStr) {
        return commandStr.substring(0, 2);
    }
}
