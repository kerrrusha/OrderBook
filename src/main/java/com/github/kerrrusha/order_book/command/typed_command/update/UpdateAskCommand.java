package com.github.kerrrusha.order_book.command.typed_command.update;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InvalidCommandTypeException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidCommandParametersException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.model.Price;
import com.github.kerrrusha.order_book.model.Size;

public class UpdateAskCommand extends UpdateCommand {
    private static final String CORRECT_KEYWORD = "ask";

    private UpdateAskCommand(String command) {
        super(command);
    }

    public static UpdateAskCommand parseCommand(String commandStr)
            throws InvalidCommandTypeException {
        TypedCommand.checkCommandString(commandStr, commandType);
        if (invalidParameters(commandStr))
            throw new InvalidCommandParametersException(INVALID_PARAMETERS);
        return new UpdateAskCommand(commandStr);
    }

    private static boolean invalidParameters(String commandStr) {
        Command command = new Command(commandStr);

        String priceStr = command.getInstructionAtIndex(1);
        String sizeStr = command.getInstructionAtIndex(2);
        String keywordStr = command.getInstructionAtIndex(3);

        return Price.invalidStringValue(priceStr) ||
                Size.invalidStringValue(sizeStr) ||
                !keywordStr.equals(CORRECT_KEYWORD);
    }
}
