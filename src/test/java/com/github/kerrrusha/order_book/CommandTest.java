package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.Command;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {
    private static class CommandTestPair {
        String command;
        int instructionsCount;

        public CommandTestPair(String command, int instructionsCount) {
            this.command = command;
            this.instructionsCount = instructionsCount;
        }
    }

    @Test
    public void instructionsCountTest() {
        List<CommandTestPair> commands = new ArrayList<>(
                List.of(
                        new CommandTestPair("q,2", 2),
                        new CommandTestPair("q", 1),
                        new CommandTestPair("u,2,5", 3)
                )
        );
        for (CommandTestPair testPair : commands) {
            Command command = new Command(testPair.command);
            assertEquals(
                    command.instructionsCount(),
                    testPair.instructionsCount,
                    "Incorrect instructionsCount value."
            );
        }
    }
}