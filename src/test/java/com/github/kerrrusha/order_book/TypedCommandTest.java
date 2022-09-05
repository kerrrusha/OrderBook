package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.*;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypedCommandTest {
    @Test
    public void parseCommandCorrectTest() {
        List<String> inputs = new ArrayList<>(
                List.of(
                        "q,2",
                        "q",
                        "u,2,5",
                        "o,2,5,jason,234",
                        "u,,"
                )
        );
        for (String testCommandStr : inputs) {
            assertThrows(InvalidCommandTypeException.class,
                    () -> TypedCommand.parseCommand(testCommandStr));
        }
    }

    @Test
    public void parseCommandIncorrectTest() {
        List<String> inputs = new ArrayList<>(
                List.of(
                        "q,2",
                        "qq",
                        "u 2 5",
                        "",
                        null
                )
        );
        for (String testCommandStr : inputs) {
            assertThrows(InvalidCommandTypeException.class,
                    () -> TypedCommand.parseCommand(testCommandStr));
        }
    }
}
