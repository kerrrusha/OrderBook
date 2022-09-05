package com.github.kerrrusha.order_book.command;

public enum CommandType {
    ANY('_'),
    QUERY('q'),
    ORDER('o'),
    UPDATE('v');

    final char CODE;

    CommandType(char CODE) {
        this.CODE = CODE;
    }
}
