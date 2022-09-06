package com.github.kerrrusha.order_book.outer_data_storage;

public interface DataRewritable {
    void rewrite(String data) throws DataStorageNotFoundException;
}
