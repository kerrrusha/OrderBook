package com.github.kerrrusha.order_book.command;

public enum CommandType {
    UPDATE('v'),
    QUERY('q'),
    ORDER('o');

    final char CODE;

    CommandType(char CODE) {
        this.CODE = CODE;
    }
}
