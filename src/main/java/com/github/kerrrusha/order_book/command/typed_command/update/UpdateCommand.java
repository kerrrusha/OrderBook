package com.github.kerrrusha.order_book.command.typed_command.update;

import com.github.kerrrusha.order_book.command.CommandType;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class UpdateCommand extends TypedCommand {
    protected static CommandType commandType = CommandType.UPDATE;

    protected UpdateCommand(String command) {
        super(command);
    }
}
