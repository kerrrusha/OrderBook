package com.github.kerrrusha.order_book.model;

import java.lang.reflect.Field;

public class Order {
    private int price;
    private int size;
    private PriceLevelType type;

    public Order(int price, int size, PriceLevelType type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int newSize) {
        size = newSize;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int newPrice) {
        price = newPrice;
    }

    public PriceLevelType getType() {
        return type;
    }
    public void setType(PriceLevelType newType) {
        type = newType;
    }

    public boolean equalsBy(String field, String toStringValue) throws NoSuchFieldException {
        Field declaredField = this.getClass().getDeclaredField(field);
        return declaredField.toString().equals(toStringValue);
    }
}
