package com.github.kerrrusha.order_book.outer_data_storage.file;

import com.github.kerrrusha.order_book.outer_data_storage.DataRewritable;
import com.github.kerrrusha.order_book.outer_data_storage.DataStorageNotFoundException;
import com.github.kerrrusha.order_book.outer_data_storage.DataWriteable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileWriter implements DataWriteable, DataRewritable {
    private static final String NOT_FOUND = "File not found at the given filepath";

    private final String filepath;

    public FileWriter(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void write(String data) throws DataStorageNotFoundException {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filepath), StandardOpenOption.APPEND)) {
            writer.write(data);
        } catch (IOException ioe) {
            throw new DataStorageNotFoundException(NOT_FOUND);
        }
    }

    @Override
    public void rewrite(String data) throws DataStorageNotFoundException {
        try {
            FileOutputStream outputStream = new FileOutputStream(filepath);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(dataBytes);
        } catch (IOException e) {
            throw new DataStorageNotFoundException(NOT_FOUND);
        }
    }
}
