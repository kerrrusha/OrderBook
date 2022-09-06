package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.CommandParser;
import com.github.kerrrusha.order_book.command.CommandParseUnsuccessfulException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidPriceStringException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidSizeStringException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestBidCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QuerySizeCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateBidCommand;
import com.github.kerrrusha.order_book.model.*;
import com.github.kerrrusha.order_book.outer_data_storage.DataStorageNotFoundException;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileReader;
import com.github.kerrrusha.order_book.outer_data_storage.file.FileWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final String INPUT_FILEPATH = "input.txt";
    private static final String OUTPUT_FILEPATH = "output.txt";

    public static void main(String[] args) {
        String commandsStr = readCommands();
        if (StringUtils.stringIsInvalid(commandsStr))
            return;

        TypedCommand[] typedCommands = getTypedCommands(commandsStr);

        String result = null;
        try {
            result = executeCommands(typedCommands);
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
        String fileContent = null;
        try {
            fileContent = reader.read();
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
    private static TypedCommand[] getTypedCommands(String commandsStr) {
        String[] splittedCommandsStr = commandsStr.split("\r\n");

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

        for (TypedCommand typedCommand : typedCommands) {
            Price price;
            Size size;
            PriceLevelType type;

            if (typedCommand instanceof UpdateAskCommand) {
                String priceStr = typedCommand.getInstructionAtIndex(1);
                String sizeStr = typedCommand.getInstructionAtIndex(2);

                price = Price.parsePrice(priceStr);
                size = Size.parseSize(sizeStr);
                type = PriceLevelType.ASK;

                Order order = new Order(price, size, type);
                book.add(order);
            }
            if (typedCommand instanceof UpdateBidCommand) {
                String priceStr = typedCommand.getInstructionAtIndex(1);
                String sizeStr = typedCommand.getInstructionAtIndex(2);

                price = Price.parsePrice(priceStr);
                size = Size.parseSize(sizeStr);
                type = PriceLevelType.BID;

                Order order = new Order(price, size, type);
                book.add(order);
            }
            if (typedCommand instanceof QueryBestAskCommand) {
                List<Order> askOrders = book.getAskOrders();
                Optional<Order> bestAskOrderOptional = askOrders.stream().
                        min(Comparator.comparingLong(ord -> ord.getPrice().get()));

                Order bestAskOrder;
                if(bestAskOrderOptional.isPresent()) {
                    bestAskOrder = bestAskOrderOptional.get();
                    result.append(bestAskOrder.getPrice()).
                            append(" ").
                            append(bestAskOrder.getSize()).
                            append("\n");
                }
            }
            if (typedCommand instanceof QueryBestBidCommand) {
                List<Order> orders = book.getBidOrders();
                Optional<Order> bestOrderOptional = orders.stream().
                        max(Comparator.comparingLong(ord -> ord.getPrice().get()));

                Order bestOrder;
                if(bestOrderOptional.isPresent()) {
                    bestOrder = bestOrderOptional.get();
                    result.append(bestOrder.getPrice()).
                            append(" ").
                            append(bestOrder.getSize()).
                            append("\n");
                }
            }
            if (typedCommand instanceof QuerySizeCommand) {
                String priceStr = typedCommand.getInstructionAtIndex(2);
                price = Price.parsePrice(priceStr);

                Optional<Order> orderOptional = book.getOrderByPrice(price);
                if(orderOptional.isPresent()) {
                    Order order = orderOptional.get();
                    result.append(order.getSize()).
                            append("\n");
                }
            }
        }

        return result.toString();
    }
    private static void createOutputFileIfNotExists() {
        try {
            new FileOutputStream(OUTPUT_FILEPATH, true).close();
        } catch (Exception ignored) {}
    }
    private static void writeResult(String data) {
        String absolutePath = new File(INPUT_FILEPATH).getAbsolutePath();
        FileWriter writer = new FileWriter(absolutePath);
        try {
            writer.rewrite(data);
        } catch (DataStorageNotFoundException e) {
            e.printStackTrace();
        }
    }
}
