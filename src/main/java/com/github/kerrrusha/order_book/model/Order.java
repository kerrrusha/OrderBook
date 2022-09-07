package com.github.kerrrusha.order_book.model;

import com.github.kerrrusha.order_book.command.typed_command.ValueOutOfRangeException;

public class Order {
    private final Price price;
    private final Size size;
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

    public PriceLevelType getType() {
        return type;
    }
    public void setType(PriceLevelType newType) {
        type = newType;
    }

    public void buyShares(Size size) throws ValueOutOfRangeException {
        this.size.subtract(size);
    }
}
