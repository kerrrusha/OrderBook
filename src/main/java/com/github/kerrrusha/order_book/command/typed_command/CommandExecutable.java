package com.github.kerrrusha.order_book.command.typed_command;

import com.github.kerrrusha.order_book.command.Command;

public interface CommandExecutable {
    String execute(Command command, Object executeAt)
            throws InvalidPriceStringException, InvalidSizeStringException;
}
