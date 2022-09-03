package com.github.kerrrusha.order_book.command;

public class TypedCommand extends Command {
    private final CommandType commandType;

    private TypedCommand(String command, CommandType type) {
        super(command);
        commandType = type;
    }

    public static TypedCommand parseCommand(String commandStr, CommandType type)
            throws InvalidAttributeValueException {
        if (!isValid(commandStr, type))
            throw new InvalidAttributeValueException("Command string is in invalid format");
        return new TypedCommand(commandStr, type);
    }

    private static boolean isValid(String commandStr, CommandType type) {
        if (!checkString(commandStr))
            return false;
        if (commandStr.length() == 1)
            return checkSingleCase(commandStr, type);
        return checkDefaultCase(commandStr, type);
    }
    private static boolean checkString(String str) {
        return str != null && str.length() != 0;
    }
    private static boolean checkSingleCase(String commandStr, CommandType type) {
        return commandStr.equals(String.valueOf(type));
    }
    private static boolean checkDefaultCase(String commandStr, CommandType type) {
        return getCommandTypeWithSeparator(commandStr).equals(String.valueOf(type) + SEPARATOR);
    }
    private static char getCommandTypeChar(String commandStr) {
        return commandStr.trim().charAt(0);
    }
    private static String getCommandTypeWithSeparator(String commandStr) {
        return commandStr.substring(0, 2);
    }
}
