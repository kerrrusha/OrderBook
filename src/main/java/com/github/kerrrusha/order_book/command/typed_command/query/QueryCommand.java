package com.github.kerrrusha.order_book.command.typed_command.query;

import com.github.kerrrusha.order_book.command.CommandType;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;

public class QueryCommand extends TypedCommand {
    protected static CommandType commandType = CommandType.QUERY;

    protected QueryCommand(String command) {
        super(command);
    }
}
