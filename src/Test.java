import java.util.Arrays;
import java.util.Scanner;
import commands.CommandHandler;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String commandLine = scanner.nextLine().trim();

            if (commandLine.isEmpty()) {
                continue;
            }

            String[] parts = commandLine.split("\\s+");
            String command = parts[0];
            String[] arguments = Arrays.copyOfRange(parts, 1, parts.length);

            try {
                CommandHandler.executeCommand(command, arguments);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}