package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.CommandParser;
import com.github.kerrrusha.order_book.command.typed_command.InvalidPriceStringException;
import com.github.kerrrusha.order_book.command.typed_command.InvalidSizeStringException;
import com.github.kerrrusha.order_book.command.typed_command.TypedCommand;
import com.github.kerrrusha.order_book.command.typed_command.order.OrderSellCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QueryBestBidCommand;
import com.github.kerrrusha.order_book.command.typed_command.query.QuerySizeCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateAskCommand;
import com.github.kerrrusha.order_book.command.typed_command.update.UpdateBidCommand;
import org.junit.jupiter.api.Test;

import static com.github.kerrrusha.order_book.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    public void readCommandsTest() {
        final String NEW_LINE = System.lineSeparator();
        final String expected = "u,9,1,bid" + NEW_LINE +
                "u,11,5,ask" + NEW_LINE +
                "q,best_bid" + NEW_LINE +
                "u,10,2,bid" + NEW_LINE +
                "q,best_bid" + NEW_LINE +
                "o,sell,1" + NEW_LINE +
                "q,size,10" + NEW_LINE +
                "u,9,0,bid" + NEW_LINE +
                "u,11,0,ask" + NEW_LINE;
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

    @Test
    public void executeCommandsTest() throws InvalidPriceStringException, InvalidSizeStringException {
        final TypedCommand[] testData = new TypedCommand[] {
                UpdateBidCommand.parseCommand("u,9,1,bid"),
                UpdateAskCommand.parseCommand("u,11,5,ask"),
                QueryBestBidCommand.parseCommand("q,best_bid"),
                UpdateBidCommand.parseCommand("u,10,2,bid"),
                QueryBestBidCommand.parseCommand("q,best_bid"),
                OrderSellCommand.parseCommand("o,sell,1"),
                QuerySizeCommand.parseCommand("q,size,10"),
                UpdateBidCommand.parseCommand("u,9,0,bid"),
                UpdateAskCommand.parseCommand("u,11,0,ask")
        };

        final String expected = """
                9,1
                10,2
                1
                """;

        String actual = Main.executeCommands(testData);

        assertEquals(expected, actual, "Incorrect commands executing result.");
    }

    @Test
    public void splitCommandsStringTest() {
        final String[] expected = new String[] {
                "u,9,1,bid",
                "u,11,5,ask",
                "q,best_bid",
                "u,10,2,bid",
                "q,best_bid",
                "o,sell,1",
                "q,size,10",
                "u,9,0,bid",
                "u,11,0,ask"
        };
        final String NEW_LINE = System.lineSeparator();
        final String commandsStr =
                "u,9,1,bid" + NEW_LINE +
                "u,11,5,ask" + NEW_LINE +
                "q,best_bid" + NEW_LINE +
                "u,10,2,bid" + NEW_LINE +
                "q,best_bid" + NEW_LINE +
                "o,sell,1" + NEW_LINE +
                "q,size,10" + NEW_LINE +
                "u,9,0,bid" + NEW_LINE +
                "u,11,0,ask" + NEW_LINE;
        String[] actual = splitCommandsString(commandsStr);

        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], "Incorrect commands splitting.");
        }
    }
}
