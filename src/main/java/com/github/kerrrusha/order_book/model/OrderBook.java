package com.github.kerrrusha.order_book.model;

import java.util.List;
import java.util.Optional;

public class OrderBook {
    private final List<Order> orders;

    public OrderBook(List<Order> orders) {
        this.orders = orders;
    }

    public Optional<Order> getOrderByPrice(int price) {
        return orders.stream().filter(t->t.getPrice() == price).findFirst();
    }
}
