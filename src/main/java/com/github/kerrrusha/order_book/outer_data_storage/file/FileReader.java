package com.github.kerrrusha.order_book.outer_data_storage.file;

import com.github.kerrrusha.order_book.outer_data_storage.DataReadable;
import com.github.kerrrusha.order_book.outer_data_storage.DataStorageNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader implements DataReadable {
    private static final String NOT_FOUND = "File not found at the given filepath";

    private final Path filepath;

    public FileReader(String filepath) {
        this.filepath = Path.of(filepath);
    }

    @Override
    public String read() throws DataStorageNotFoundException {
        try {
            return Files.readString(filepath);
        } catch (IOException e) {
            throw new DataStorageNotFoundException(NOT_FOUND);
        }
    }
}
