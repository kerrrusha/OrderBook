package com.github.kerrrusha.order_book.command;

public enum CommandType {
    ANY('_'),
    QUERY('q'),
    ORDER('o'),
    UPDATE('u');

    final char CODE;

    CommandType(char CODE) {
        this.CODE = CODE;
    }
}
