package com.github.kerrrusha.order_book.command.typed_command.order;

import com.github.kerrrusha.order_book.command.CommandType;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class OrderCommand extends TypedCommand {
    static{
        commandType = CommandType.UPDATE;
    }

    protected OrderCommand(String command) {
        super(command);
    }
}
