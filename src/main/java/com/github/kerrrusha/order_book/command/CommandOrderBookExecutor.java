package com.github.kerrrusha.order_book.command;

import com.github.kerrrusha.order_book.command.typed_command.InvalidPriceStringException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidSizeStringException;
import com.github.kerrrusha.order_book.command.typed_command.ValueOutOfRangeException;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderBuyCommand;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderSellCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestBidCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QuerySizeCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateBidCommand;
import com.github.kerrrusha.order_book.model.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandOrderBookExecutor implements CommandExecutable {
    private static final String INNER_SEPARATOR = ",";
    private static final String ENDLINE_SEPARATOR = "\n";

    @Override
    public String execute(Command typedCommand, Object executeAt)
            throws InvalidPriceStringException, InvalidSizeStringException, ValueOutOfRangeException {
        if (! (executeAt instanceof OrderBook) )
            throw new ClassCastException("Executable At object is not OrderBook instance");
        OrderBook book = (OrderBook) executeAt;

        StringBuilder result = new StringBuilder();

        if (typedCommand instanceof UpdateAskCommand) {
            executeUpdateAskCommand(typedCommand, book);
        }
        if (typedCommand instanceof UpdateBidCommand) {
            executeUpdateBidCommand(typedCommand, book);
        }
        if (typedCommand instanceof QueryBestAskCommand) {
            Optional<String> queryResult = executeQueryBestAskCommand(book);
            queryResult.ifPresent(result::append);
        }
        if (typedCommand instanceof QueryBestBidCommand) {
            Optional<String> queryResult = executeQueryBestBidCommand(book);
            queryResult.ifPresent(result::append);
        }
        if (typedCommand instanceof QuerySizeCommand) {
            result.append(executeQuerySizeCommand(typedCommand, book));
        }
        if (typedCommand instanceof OrderBuyCommand) {
            executeOrderBuyCommand(typedCommand, book);
        }
        if (typedCommand instanceof OrderSellCommand) {
            executeOrderSellCommand(typedCommand, book);
        }

        return result.toString();
    }

    private static void executeUpdateAskCommand(Command typedCommand, OrderBook book) throws InvalidPriceStringException, InvalidSizeStringException, ValueOutOfRangeException {
        String priceStr = typedCommand.getInstructionAtIndex(1);
        String sizeStr = typedCommand.getInstructionAtIndex(2);

        Price price = Price.parsePrice(priceStr);
        Size size = Size.parseSize(sizeStr);
        PriceLevelType type = PriceLevelType.ASK;

        updateOrder(book, price, size, type);
    }
    private static void executeUpdateBidCommand(Command typedCommand, OrderBook book) throws InvalidPriceStringException, InvalidSizeStringException, ValueOutOfRangeException {
        String priceStr = typedCommand.getInstructionAtIndex(1);
        String sizeStr = typedCommand.getInstructionAtIndex(2);

        Price price = Price.parsePrice(priceStr);
        Size size = Size.parseSize(sizeStr);
        PriceLevelType type = PriceLevelType.BID;

        updateOrder(book, price, size, type);
    }
    private static Optional<String> executeQueryBestAskCommand(OrderBook book) {
        Optional<String> result = Optional.empty();

        List<Order> askOrders = book.getAskOrders();
        Optional<Order> bestAskOrderOptional = askOrders.stream().
                filter(order -> order.getSize().get() != 0).
                min(Comparator.comparingLong(ord -> ord.getPrice().get()));

        Order bestAskOrder;
        if(bestAskOrderOptional.isPresent()) {
            bestAskOrder = bestAskOrderOptional.get();
            result = Optional.of(
                    bestAskOrder.getPrice() +
                          INNER_SEPARATOR +
                          bestAskOrder.getSize() +
                          ENDLINE_SEPARATOR
                    );
        }

        return result;
    }
    private static Optional<String> executeQueryBestBidCommand(OrderBook book) {
        Optional<String> result = Optional.empty();

        List<Order> bidOrders = book.getBidOrders();
        Optional<Order> bestOrderOptional = bidOrders.stream().
                filter(order -> order.getSize().get() != 0).
                max(Comparator.comparingLong(ord -> ord.getPrice().get()));

        Order bestOrder;
        if(bestOrderOptional.isPresent()) {
            bestOrder = bestOrderOptional.get();
            result = Optional.of(
                    bestOrder.getPrice() +
                          INNER_SEPARATOR +
                          bestOrder.getSize() +
                          ENDLINE_SEPARATOR
            );
        }

        return result;
    }
    private static String executeQuerySizeCommand(Command typedCommand, OrderBook book) throws InvalidPriceStringException {
        String priceStr = typedCommand.getInstructionAtIndex(2);
        Price price = Price.parsePrice(priceStr);

        Optional<Order> orderOptional = book.getOrderByPrice(price);
        if(orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return order.getSize() + ENDLINE_SEPARATOR;
        }
        return "0" + ENDLINE_SEPARATOR;
    }
    private static void executeOrderBuyCommand(Command typedCommand, OrderBook book) throws InvalidSizeStringException, ValueOutOfRangeException {
        String sizeStr = typedCommand.getInstructionAtIndex(2);
        Size size = Size.parseSize(sizeStr);

        List<Order> cheapOrders = book.getAskOrders().stream().
                filter(order -> order.getSize().get() != 0).
                sorted(Comparator.comparingLong(ord -> ord.getPrice().get())).
                collect(Collectors.toList());
        buyShares(size, cheapOrders);
    }
    private static void executeOrderSellCommand(Command typedCommand, OrderBook book) throws InvalidSizeStringException, ValueOutOfRangeException {
        String sizeStr = typedCommand.getInstructionAtIndex(2);
        Size size = Size.parseSize(sizeStr);

        List<Order> expensiveOrders = book.getBidOrders().stream().
                filter(order -> order.getSize().get() != 0).
                sorted(Comparator.comparingLong(ord -> ord.getPrice().get())).
                collect(Collectors.toList());
        Collections.reverse(expensiveOrders);
        buyShares(size, expensiveOrders);
    }

    private static void buyShares(Size size, List<Order> orders) throws ValueOutOfRangeException {
        for (Order order : orders) {
            if (order.getSize().greaterThanOrEqual(size)) {
                order.buyShares(size);
                break;
            }
            size.subtract(order.getSize());
            order.setSize(0);
        }
    }

    private static void updateOrder(OrderBook book, Price price, Size size, PriceLevelType type) throws ValueOutOfRangeException {
        Optional<Order> orderFound = book.getOrderByPrice(price);
        if (orderFound.isPresent()) {
            Order order = orderFound.get();

            order.setSize(size.get());
            order.setType(type);
        }
        else {
            book.add(new Order(price, size, type));
        }
    }
}
