package commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandHandlerTest {
    @Test
    public void testInvalidCommand() {
        String command = "abc";
        String[] arguments = {};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CommandHandler.executeCommand(command, arguments));
        assertEquals("Command 'abc' not found", exception.getMessage());
    }

    @Test
    public void testInvalidArgumentCount() {
        String command = "quit";
        String[] arguments = {"arg1"};
        Exception exception = assertThrows(IllegalArgumentException.class, () -> CommandHandler.executeCommand(command, arguments));
        assertEquals("Illegal argument count", exception.getMessage());
    }
}