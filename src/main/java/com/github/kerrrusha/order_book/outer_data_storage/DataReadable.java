package com.github.kerrrusha.order_book.outer_data_storage;

public interface DataReadable {
    String read() throws DataStorageNotFoundException;
}
