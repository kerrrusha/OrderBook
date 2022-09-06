package com.github.kerrrusha.order_book.model;

import com.github.kerrrusha.order_book.StringUtils;
import com.github.kerrrusha.order_book.command.CommandValidationUtils;
import com.github.kerrrusha.order_book.command.typed_command.InvalidPriceStringException;
import com.github.kerrrusha.order_book.command.typed_command.ValueOutOfRangeException;

public class Price {
    private static final String INVALID_STRING = "String is invalid typed";
    private static final String VALUE_OUT_OF_RANGE = "Value out of range";

    private long price;

    private Price(String priceStr) {
        price = Long.parseLong(priceStr);
    }
    private Price(long price) {
        this.price = price;
    }

    public static Price parsePrice(String priceStr) throws InvalidPriceStringException {
        if (invalidStringValue(priceStr))
            throw new InvalidPriceStringException(INVALID_STRING);
        return new Price(priceStr);
    }
    public static Price valueOf(long price) throws ValueOutOfRangeException {
        if (invalidValue(price))
            throw new ValueOutOfRangeException(VALUE_OUT_OF_RANGE);
        return new Price(price);
    }

    public long get() { return price; }
    public void set(long price) throws ValueOutOfRangeException {
        if (invalidValue(price))
            throw new ValueOutOfRangeException(VALUE_OUT_OF_RANGE);
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Price)) {
            return false;
        }

        Price other = (Price) o;

        return this.price == other.price;
    }
    @Override
    public String toString() {
        return "" + price;
    }

    public static boolean invalidStringValue(String priceStr) {
        if (StringUtils.stringIsInvalid(priceStr))
            return true;
        long price;
        try {
            price = Long.parseLong(priceStr);
        } catch (NumberFormatException e) {
            return true;
        }
        return invalidValue(price);
    }
    private static boolean invalidValue(long price) {
        return price < 1 || price > (long)10e8;
    }
}
