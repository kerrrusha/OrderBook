package com.github.kerrrusha.order_book.command.typed_command.order;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.model.Size;

public class OrderBuyCommand extends OrderCommand {
    private static final String CORRECT_KEYWORD = "buy";

    private OrderBuyCommand(String command) {
        super(command);
    }

    public static OrderBuyCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new OrderBuyCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        String keywordStr = command.getInstructionAtIndex(1);
        String sizeStr = command.getInstructionAtIndex(2);

        return !keywordStr.equals(CORRECT_KEYWORD) ||
                Size.invalidStringValue(sizeStr);
    }
}
