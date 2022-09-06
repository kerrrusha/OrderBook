package com.github.kerrrusha.order_book.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderBook {
    private final List<Order> orders;

    public OrderBook() {
        this.orders = List.of();
    }
    public OrderBook(List<Order> orders) {
        this.orders = orders;
    }

    public Optional<Order> getOrderByPrice(Price price) {
        return orders.stream().filter(t->t.getPrice().equals(price)).findFirst();
    }
    public List<Order> getAskOrders() {
        return orders.stream().
                filter(t -> t.getType() == PriceLevelType.ASK).
                collect(Collectors.toList());
    }
    public List<Order> getBidOrders() {
        return orders.stream().
                filter(t -> t.getType() == PriceLevelType.BID).
                collect(Collectors.toList());
    }
    public void add(Order newOrder) {
        orders.add(newOrder);
    }

    @Override
    public String toString() {
        return ;
    }
}
