package com.github.kerrrusha.order_book.command.typed_command;

public class ValueOutOfRangeException extends Throwable {
    public ValueOutOfRangeException(String msg) {
        super(msg);
    }
}
