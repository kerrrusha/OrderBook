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
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String INPUT_FILEPATH = "input.txt";
    private static final String OUTPUT_FILEPATH = "output.txt";
    private static final String FILE_COMMANDS_SEPARATOR = "\n";
    private static final String FILE_COMMANDS_LARGE_SEPARATOR = "\r\n";

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
        result = removeStringLastNewLineCharacter(result);

        createOutputFileIfNotExists();
        writeResult(result);
    }

    public static String readCommands() {
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
    public static TypedCommand getTypedCommand(String commandStr) throws CommandParseUnsuccessfulException {
        return CommandParser.parseCommand(commandStr);
    }
    private static TypedCommand[] getTypedCommands(String[] splittedCommandsStr) {
        List<TypedCommand> typedCommands = new ArrayList<>();
        for (String commandStr : splittedCommandsStr) {
            TypedCommand command;
            try {
                command = getTypedCommand(commandStr);
            } catch (CommandParseUnsuccessfulException e) {
                continue;
            }
            typedCommands.add(command);
        }

        return typedCommands.toArray(new TypedCommand[0]);
    }
    public static String executeCommands(TypedCommand[] typedCommands) throws InvalidPriceStringException, InvalidSizeStringException {
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
