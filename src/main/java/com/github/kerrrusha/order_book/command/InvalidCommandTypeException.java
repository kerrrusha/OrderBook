package com.github.kerrrusha.order_book.command;

public class InvalidCommandTypeException extends IllegalArgumentException {
    public InvalidCommandTypeException(String message) {
        super(message);
    }
}
