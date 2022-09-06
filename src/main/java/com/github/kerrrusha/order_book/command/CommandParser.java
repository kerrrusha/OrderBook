package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderBuyCommand;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderSellCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestBidCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QuerySizeCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateBidCommand;

public class CommandParser {
    private static final String PARSE_UNSUCCESSFUL = "Cannot parse command string into typed command object";

    public static TypedCommand parseCommand(String commandStr) {
        TypedCommand typedCommand;

        typedCommand = tryParseUpdate(commandStr);
        if (typedCommand != null)
            return typedCommand;

        typedCommand = tryParseQuery(commandStr);
        if (typedCommand != null)
            return typedCommand;

        typedCommand = tryParseOrder(commandStr);
        if (typedCommand != null)
            return typedCommand;

        throw new CommandParseUnsuccessfulException(PARSE_UNSUCCESSFUL);
    }

    private static TypedCommand tryParseUpdate(String commandStr) {
        TypedCommand typedCommand = null;

        try {
            typedCommand = UpdateAskCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}
        try {
            typedCommand = UpdateBidCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}

        return typedCommand;
    }
    private static TypedCommand tryParseQuery(String commandStr) {
        TypedCommand typedCommand = null;

        try {
            typedCommand = QuerySizeCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}
        try {
            typedCommand = QueryBestAskCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}
        try {
            typedCommand = QueryBestBidCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}

        return typedCommand;
    }
    private static TypedCommand tryParseOrder(String commandStr) {
        TypedCommand typedCommand = null;

        try {
            typedCommand = OrderBuyCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}
        try {
            typedCommand = OrderSellCommand.parseCommand(commandStr);
        } catch (Exception ignored) {}

        return typedCommand;
    }
}
