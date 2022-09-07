package com.github.kerrrusha.order_book.command.typed_command.order;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.model.Size;

public class OrderSellCommand extends OrderCommand {
    private static final String CORRECT_KEYWORD = "sell";

    private OrderSellCommand(String command) {
        super(command);
    }

    public static OrderSellCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr, commandType);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new OrderSellCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        String keywordStr = command.getInstructionAtIndex(1);
        String sizeStr = command.getInstructionAtIndex(2);

        return !keywordStr.equals(CORRECT_KEYWORD) ||
                Size.invalidStringValue(sizeStr);
    }
}
