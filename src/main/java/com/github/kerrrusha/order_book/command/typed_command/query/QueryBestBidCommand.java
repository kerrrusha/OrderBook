package com.github.kerrrusha.order_book.command.typed_command.query;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class QueryBestBidCommand extends QueryCommand {
    private static final String CORRECT_KEYWORD = "best_bid";

    private QueryBestBidCommand(String command) {
        super(command);
    }

    public static QueryBestBidCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr, commandType);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new QueryBestBidCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        String keywordStr = command.getInstructionAtIndex(1);

        return !keywordStr.equals(CORRECT_KEYWORD);
    }
}
