package com.github.kerrrusha.order_book.outer_data_storage;

import java.io.FileNotFoundException;

public class DataStorageNotFoundException extends FileNotFoundException {
    public DataStorageNotFoundException(String msg) {
        super(msg);
    }
}
