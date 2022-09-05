package com.github.kerrrusha.order_book.command.typed_command;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.CommandType;
import com.github.kerrrusha.order_book.command.CommandValidationUtils;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.model.Price;
import com.github.kerrrusha.order_book.model.Size;

public class TypedCommand extends Command {
    protected static final String INVALID_COMMAND_TYPE = "Command string have invalid command type.";
    protected static final String INVALID_PARAMETERS = "Command string have invalid parameters.";
    protected static CommandType commandType;

    static{
        commandType = CommandType.ANY;
    }

    protected TypedCommand(String command) {
        super(command);
    }

    public static TypedCommand parseCommand(String commandStr) throws InvalidCommandTypeException {
        checkCommandString(commandStr);
        return new TypedCommand(commandStr);
    }
    protected static void checkCommandString(String commandStr) {
        if (CommandValidationUtils.invalidCommandType(commandStr, commandType))
            throw new InvalidCommandTypeException(INVALID_COMMAND_TYPE);
    }
}
