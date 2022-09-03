package com.github.kerrrusha.order_book.command;

public class InvalidAttributeValueException extends IllegalArgumentException {
    public InvalidAttributeValueException(String message) {
        super(message);
    }
}
