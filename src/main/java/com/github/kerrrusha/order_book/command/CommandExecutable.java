package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.typed_command.InvalidPriceStringException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidSizeStringException;

public interface CommandExecutable {
    String execute(Command command, Object executeAt)
            throws InvalidPriceStringException, InvalidSizeStringException;
}
