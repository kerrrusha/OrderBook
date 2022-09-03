package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TypedCommandTest {
    @Test
    public void parseCommandCorrectTest() {
        class TestInputData {
            final String command;
            final CommandType type;

            public TestInputData(String command, CommandType type) {
                this.command = command;
                this.type = type;
            }
        }

        List<TestInputData> inputs = new ArrayList<>(
                List.of(
                        new TestInputData("q,2", CommandType.QUERY),
                        new TestInputData("q", CommandType.QUERY),
                        new TestInputData("u,2,5", CommandType.UPDATE),
                        new TestInputData("o,2,5,jason,234", CommandType.ORDER),
                        new TestInputData("u,,", CommandType.UPDATE)
                )
        );
        for (TestInputData testInputData : inputs) {
            assertThrows(InvalidAttributeValueException.class,
                    () -> TypedCommand.parseCommand(testInputData.command, testInputData.type));
        }
    }

    @Test
    public void parseCommandIncorrectTest() {
        class TestInputData {
            final String command;
            final CommandType type;

            public TestInputData(String command, CommandType type) {
                this.command = command;
                this.type = type;
            }
        }

        List<TestInputData> inputs = new ArrayList<>(
                List.of(
                        new TestInputData("q,2", CommandType.UPDATE),
                        new TestInputData("qq", CommandType.QUERY),
                        new TestInputData("u 2 5", CommandType.UPDATE),
                        new TestInputData("", CommandType.ORDER),
                        new TestInputData(null, CommandType.UPDATE)
                )
        );
        for (TestInputData testInputData : inputs) {
            assertThrows(InvalidAttributeValueException.class,
                    () -> TypedCommand.parseCommand(testInputData.command, testInputData.type));
        }
    }
}
