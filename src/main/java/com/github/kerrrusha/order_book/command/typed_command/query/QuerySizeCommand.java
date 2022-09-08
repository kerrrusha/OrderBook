package com.github.kerrrusha.order_book.command.typed_command.query;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.model.Price;

public class QuerySizeCommand extends QueryCommand {
    private static final String CORRECT_KEYWORD = "size";

    private QuerySizeCommand(String command) {
        super(command);
    }

    public static QuerySizeCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr, commandType);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new QuerySizeCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        return !command.getInstructionAtIndex(1).equals(CORRECT_KEYWORD) ||
                Price.invalidStringValue(command.getInstructionAtIndex(2));
    }
}
