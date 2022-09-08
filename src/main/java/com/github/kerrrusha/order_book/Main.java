package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.CommandOrderBookExecutor;
import com.github.kerrrusha.order_book.command.CommandParser;
import com.github.kerrrusha.order_book.command.CommandParseUnsuccessfulException;
import com.github.kerrrusha.order_book.command.typed_command.*;
import com.github.kerrrusha.order_book.model.*;
import com.github.kerrrusha.order_book.outer_data_storage.DataStorageNotFoundException;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileReader;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileWriter;

import java.io.File;
import java.io.FileOutputStream;

public class Main {
    private static final String INPUT_FILEPATH = "input.txt";
    private static final String OUTPUT_FILEPATH = "output.txt";
    private static final String FILE_COMMANDS_SEPARATOR = "\n";
    private static final String FILE_COMMANDS_LARGE_SEPARATOR = "\r\n";

    public static void main(String[] args) {
        String result = "";

        String commandsStr = readCommands();
        if (StringUtils.stringIsInvalid(commandsStr)) {
            return;
        }

        TypedCommand[] typedCommands = getTypedCommands(splitCommandsString(commandsStr));

        try {
            result += executeCommands(typedCommands);
        } catch (InvalidPriceStringException | InvalidSizeStringException | ValueOutOfRangeException e) {
            e.printStackTrace();
        }

        saveResult(result);
    }

    private static void saveResult(String result) {
        createOutputFileIfNotExists();
        writeResult(removeStringLastNewLineCharacter(result));
    }
    public static String readCommands() {
        String fileContent = "";
        try {
            fileContent = new FileReader(new File(INPUT_FILEPATH).getAbsolutePath()).read();
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    public static TypedCommand getTypedCommand(String commandStr) throws CommandParseUnsuccessfulException {
        return CommandParser.parseCommand(commandStr);
    }
    private static TypedCommand[] getTypedCommands(String[] splittedCommandsStr) {
        TypedCommand[] typedCommands = new TypedCommand[splittedCommandsStr.length];
        for (int i = 0; i < splittedCommandsStr.length; i++) {
            TypedCommand command;
            try {
                command = getTypedCommand(splittedCommandsStr[i]);
            } catch (CommandParseUnsuccessfulException e) {
                continue;
            }
            typedCommands[i] = command;
        }

        return typedCommands;
    }
    public static String executeCommands(TypedCommand[] typedCommands) throws InvalidPriceStringException,
            InvalidSizeStringException, ValueOutOfRangeException {
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
        try {
            new FileWriter(new File(OUTPUT_FILEPATH).getAbsolutePath()).rewrite(data);
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static String[] splitCommandsString(String commandsStr) {
        if (commandsStr.contains(FILE_COMMANDS_LARGE_SEPARATOR))
            return commandsStr.split(FILE_COMMANDS_LARGE_SEPARATOR);
        return commandsStr.split(FILE_COMMANDS_SEPARATOR);
    }
    public static String removeStringLastNewLineCharacter(String str) {
        char newLineCharacter = '\n';
        int NEW_CHARACTER_LENGTH = 1;
        if (StringUtils.stringIsInvalid(str) || str.length() < NEW_CHARACTER_LENGTH)
            return str;
        int lastNewLineIndex = str.lastIndexOf(newLineCharacter);
        return str.substring(0, lastNewLineIndex);
    }
}
