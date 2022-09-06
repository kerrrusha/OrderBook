package com.github.kerrrusha.order_book.model;

import com.github.kerrrusha.order_book.command.typed_command.ValueOutOfRangeException;

import java.lang.reflect.Field;

public class Order {
    private Price price;
    private Size size;
    private PriceLevelType type;

    public Order(Price price, Size size, PriceLevelType type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public Size getSize() {
        return size;
    }
    public void setSize(long newSize) throws ValueOutOfRangeException {
        size.set(newSize);
    }

    public Price getPrice() {
        return price;
    }
    public void setPrice(long newPrice) throws ValueOutOfRangeException {
        price.set(newPrice);
    }

    public PriceLevelType getType() {
        return type;
    }
    public void setType(PriceLevelType newType) {
        type = newType;
    }

    public boolean equalsBy(String field, String toStringFieldValue) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = this.getClass().getDeclaredField(field);
        return declaredField.get(this).toString().equals(toStringFieldValue);
    }
}
