package com.github.kerrrusha.order_book.command.typed_command;

public class InvalidPriceStringException extends Throwable {
    public InvalidPriceStringException(String msg) {
        super(msg);
    }
}
