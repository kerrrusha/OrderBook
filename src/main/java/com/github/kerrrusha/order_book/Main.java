package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.CommandParser;
import com.github.kerrrusha.order_book.command.CommandParseUnsuccessfulException;
import com.github.kerrrusha.order_book.command.typed_command.*;
import com.github.kerrrusha.order_book.model.*;
import com.github.kerrrusha.order_book.outer_data_storage.DataStorageNotFoundException;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileReader;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String INPUT_FILEPATH = "input.txt";
    private static final String OUTPUT_FILEPATH = "output.txt";
    private static final String FILE_COMMANDS_SEPARATOR = "\n";

    public static void main(String[] args) {
        String commandsStr = readCommands();
        if (StringUtils.stringIsInvalid(commandsStr))
            return;

        String[] splittedCommandsStr = splitCommandsString(commandsStr);
        TypedCommand[] typedCommands = getTypedCommands(splittedCommandsStr);

        String result = "";
        try {
            result += executeCommands(typedCommands);
        } catch (InvalidPriceStringException | InvalidSizeStringException e) {
            e.printStackTrace();
        }

        if (StringUtils.stringIsInvalid(result))
            return;

        createOutputFileIfNotExists();
        writeResult(result);
    }

    private static String readCommands() {
        String absolutePath = new File(INPUT_FILEPATH).getAbsolutePath();
        FileReader reader = new FileReader(absolutePath);
        String fileContent = "";
        try {
            fileContent = reader.read();
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    private static TypedCommand[] getTypedCommands(String[] splittedCommandsStr) {
        List<TypedCommand> typedCommands = new ArrayList<>();
        for (String commandStr : splittedCommandsStr) {
            TypedCommand command;
            try {
                command = CommandParser.parseCommand(commandStr);
            } catch (CommandParseUnsuccessfulException e) {
                continue;
            }
            typedCommands.add(command);
        }

        return typedCommands.toArray(new TypedCommand[0]);
    }
    private static String executeCommands(TypedCommand[] typedCommands) throws InvalidPriceStringException, InvalidSizeStringException {
        OrderBook book = new OrderBook();
        StringBuilder result = new StringBuilder();

        CommandOrderBookExecutor executor = new CommandOrderBookExecutor();

        for (TypedCommand typedCommand : typedCommands) {
            result.append(executor.execute(typedCommand, book));
        }

        return result.toString();
    }
    private static void createOutputFileIfNotExists() {
        try {
            new FileOutputStream(OUTPUT_FILEPATH, true).close();
        } catch (Exception ignored) {}
    }
    private static void writeResult(String data) {
        String absolutePath = new File(OUTPUT_FILEPATH).getAbsolutePath();
        FileWriter writer = new FileWriter(absolutePath);
        try {
            writer.rewrite(data);
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static String[] splitCommandsString(String commandsStr) {
        return commandsStr.split(FILE_COMMANDS_SEPARATOR);
    }
}
