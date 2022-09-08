package com.github.kerrrusha.order_book.command.typed_command.query;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class QueryBestAskCommand extends QueryCommand {
    private static final String CORRECT_KEYWORD = "best_ask";

    private QueryBestAskCommand(String command) {
        super(command);
    }

    public static QueryBestAskCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr, commandType);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new QueryBestAskCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        return !command.getInstructionAtIndex(1).equals(CORRECT_KEYWORD);
    }
}
