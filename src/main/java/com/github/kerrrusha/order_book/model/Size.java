package com.github.kerrrusha.order_book.model;

import com.github.kerrrusha.order_book.StringUtils;
import com.github.kerrrusha.order_book.command.CommandValidationUtils;
import com.github.kerrrusha.order_book.command.typed_command.InvalidSizeStringException;
import com.github.kerrrusha.order_book.command.typed_command.ValueOutOfRangeException;

public class Size {
    private static final String INVALID_STRING = "String is invalid typed";
    private static final String VALUE_OUT_OF_RANGE = "Value out of range";

    private long size;

    private Size(String sizeStr) {
        size = Long.parseLong(sizeStr);
    }
    private Size(long size) {
        this.size = size;
    }

    public static Size parseSize(String sizeStr) throws InvalidSizeStringException {
        if (invalidStringValue(sizeStr))
            throw new InvalidSizeStringException(INVALID_STRING);
        return new Size(sizeStr);
    }
    public static Size valueOf(long size) throws ValueOutOfRangeException {
        if (invalidValue(size))
            throw new ValueOutOfRangeException(VALUE_OUT_OF_RANGE);
        return new Size(size);
    }

    public long get() { return size; }
    public void set(long size) throws ValueOutOfRangeException {
        if (invalidValue(size))
            throw new ValueOutOfRangeException(VALUE_OUT_OF_RANGE);
        this.size = size;
    }

    @Override
    public String toString() {
        return "" + size;
    }

    public static boolean invalidStringValue(String sizeStr) {
        if (StringUtils.stringIsInvalid(sizeStr))
            return true;
        long size;
        try {
            size = Long.parseLong(sizeStr);
        } catch (NumberFormatException e) {
            return true;
        }
        return invalidValue(size);
    }
    private static boolean invalidValue(long size) {
        return size < 0 || size > (long)10e7;
    }
}
