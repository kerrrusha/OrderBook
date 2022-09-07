package com.github.kerrrusha.order_book.command.typed_command.order;

import com.github.kerrrusha.order_book.command.CommandType;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class OrderCommand extends TypedCommand {
    protected static CommandType commandType = CommandType.ORDER;

    protected OrderCommand(String command) {
        super(command);
    }
}
