package com.github.kerrrusha.order_book.command.typed_command;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderBuyCommand;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderSellCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestBidCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QuerySizeCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateBidCommand;
import com.github.kerrrusha.order_book.model.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CommandOrderBookExecutor implements CommandExecutable {
    @Override
    public String execute(Command typedCommand, Object executeAt)
            throws InvalidPriceStringException, InvalidSizeStringException {
        if (! (executeAt instanceof OrderBook) )
            throw new ClassCastException("Executable At object is not OrderBook instance");
        OrderBook book = (OrderBook) executeAt;

        StringBuilder result = new StringBuilder();

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
        if (typedCommand instanceof OrderBuyCommand) {
            String sizeStr = typedCommand.getInstructionAtIndex(2);
            size = Size.parseSize(sizeStr);

            List<Order> askOrders = book.getAskOrders();
            Optional<Order> bestAskOrderOptional = askOrders.stream().
                    min(Comparator.comparingLong(ord -> ord.getPrice().get()));

            Order bestAskOrder;
            if (bestAskOrderOptional.isPresent()) {
                bestAskOrder = bestAskOrderOptional.get();
                long newSizeValue = bestAskOrder.getSize().get() - size.get();
                try {
                    bestAskOrder.setSize(newSizeValue);
                } catch (ValueOutOfRangeException e) {
                    e.printStackTrace();
                }
            }
        }
        if (typedCommand instanceof OrderSellCommand) {
            String sizeStr = typedCommand.getInstructionAtIndex(2);
            size = Size.parseSize(sizeStr);

            List<Order> bidOrders = book.getBidOrders();
            Optional<Order> bestBidOrderOptional = bidOrders.stream().
                    max(Comparator.comparingLong(ord -> ord.getPrice().get()));

            Order bestBidOrder;
            if (bestBidOrderOptional.isPresent()) {
                bestBidOrder = bestBidOrderOptional.get();
                long newSizeValue = bestBidOrder.getSize().get() - size.get();
                try {
                    bestBidOrder.setSize(newSizeValue);
                } catch (ValueOutOfRangeException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }
}
