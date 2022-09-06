package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.CommandParser;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import org.junit.jupiter.api.Test;

import static com.github.kerrrusha.order_book.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    public void readCommandsTest() {
        final String expected = """
                u,9,1,bid
                u,11,5,ask
                q,best_bid
                u,10,2,bid
                q,best_bid
                o,sell,1
                q,size,10
                u,9,0,bid
                u,11,0,ask
                """;
        String actual = readCommands();

        assertEquals(expected, actual, "Incorrect file reading.");
    }

    @Test
    public void getTypedCommandTest() {
        class TestInputData {
            final String commandStr;
            final TypedCommand expectedCommand;
            public TestInputData(String commandStr) {
                this.commandStr = commandStr;
                this.expectedCommand = CommandParser.parseCommand(commandStr);
            }
        }

        final TestInputData[] testData = new TestInputData[] {
                new TestInputData("u,9,1,bid"),
                new TestInputData("u,11,5,ask"),
                new TestInputData("q,best_bid"),
                new TestInputData("u,10,2,bid"),
                new TestInputData("q,best_bid"),
                new TestInputData("o,sell,1"),
                new TestInputData("q,size,10"),
                new TestInputData("u,9,0,bid"),
                new TestInputData("u,11,0,ask")
        };

        for (TestInputData data : testData) {
            TypedCommand actual = getTypedCommand(data.commandStr);
            assertEquals(data.expectedCommand, actual, "Incorrect typed command.");
        }
    }
}