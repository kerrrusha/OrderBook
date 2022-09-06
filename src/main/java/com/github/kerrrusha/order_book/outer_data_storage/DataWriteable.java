package com.github.kerrrusha.order_book.outer_data_storage;

public interface DataWriteable {
    void write(String data) throws DataStorageNotFoundException;
}
