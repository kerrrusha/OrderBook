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
        TypedCommand.checkCommandString(commandStr);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new QuerySizeCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        String keywordStr = command.getInstructionAtIndex(1);
        String priceStr = command.getInstructionAtIndex(2);

        return !keywordStr.equals(CORRECT_KEYWORD) ||
                Price.invalidStringValue(priceStr);
    }
}
