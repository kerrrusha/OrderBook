package com.github.kerrrusha.order_book;

import com.github.kerrrusha.order_book.command.Command;
import com.github.kerrrusha.order_book.command.InstructionIndexOutOfBoundsException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandTest {
    @Test
    public void instructionsCountTest() {
        class TestInputData {
            final String command;
            final int expectedInstructionsCount;

            public TestInputData(String command, int expectedInstructionsCount) {
                this.command = command;
                this.expectedInstructionsCount = expectedInstructionsCount;
            }
        }

        List<TestInputData> inputs = new ArrayList<>(
                List.of(
                        new TestInputData("q,2", 2),
                        new TestInputData("q", 1),
                        new TestInputData("", 0),
                        new TestInputData("u,2,5", 3),
                        new TestInputData(",,", 3)
                )
        );
        for (TestInputData testInputData : inputs) {
            Command command = new Command(testInputData.command);
            assertEquals(
                    testInputData.expectedInstructionsCount,
                    command.instructionsCount(),
                    "Incorrect instructionsCount value."
            );
        }
    }

    @Test
    public void getInstructionAtIndexTest() {
        class TestInputData {
            final String command;
            final int instructionIndex;
            final String expectedInstruction;

            public TestInputData(String command,
                                   int instructionIndex, String expectedInstruction) {
                this.command = command;
                this.instructionIndex = instructionIndex;
                this.expectedInstruction = expectedInstruction;
            }
        }

        List<TestInputData> inputs = new ArrayList<>(
                List.of(
                        new TestInputData("q,2", 1, "2"),
                        new TestInputData("q", 0, "q"),
                        new TestInputData("u,2,5", 1, "2"),
                        new TestInputData(",,", 0, "")
                )
        );
        for (TestInputData testInputData : inputs) {
            Command command = new Command(testInputData.command);
            assertEquals(
                    testInputData.expectedInstruction,
                    command.getInstructionAtIndex(testInputData.instructionIndex),
                    "Incorrect instruction value."
            );
        }
    }

    @Test
    public void getInstructionAtIndexErrorTest() {
        class TestInputData {
            final String command;
            final int instructionIndex;

            public TestInputData(String command, int instructionIndex) {
                this.command = command;
                this.instructionIndex = instructionIndex;
            }
        }

        List<TestInputData> inputs = new ArrayList<>(
                List.of(
                        new TestInputData("q,2", -1),
                        new TestInputData("q", 10),
                        new TestInputData("u,2,5", 3)
                )
        );
        for (TestInputData testInputData : inputs) {
            Command command = new Command(testInputData.command);
            assertThrows(InstructionIndexOutOfBoundsException.class,
                    () -> command.getInstructionAtIndex(testInputData.instructionIndex));
        }
    }
}
