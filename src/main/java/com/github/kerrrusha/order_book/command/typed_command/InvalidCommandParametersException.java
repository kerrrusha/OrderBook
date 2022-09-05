package com.github.kerrrusha.order_book.command.typed_command;

import java.security.InvalidParameterException;

public class InvalidCommandParametersException extends InvalidParameterException {
    public InvalidCommandParametersException(String message) {
        super(message);
    }
}
