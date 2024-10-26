package commands;

import services.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private static final Map<String, Command> COMMANDS = createCommands();

    private static Map<String, Command> createCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("quit", new Command(new int[] { 0 }, args -> quit()));
        commands.put("register", new Command(new int[] { 5 }, CommandHandler::register));
        commands.put("login", new Command(new int[] { 2 }, CommandHandler::login));
        commands.put("logout", new Command(new int[] { 0, 1 }, CommandHandler::logout));
        commands.put("switch", new Command(new int[] { 1 }, CommandHandler::switchUser));
        commands.put("printInfo", new Command(new int[] { 0, 1 }, CommandHandler::printInfo));
        commands.put("createCourse", new Command(new int[] { 4 }, CommandHandler::createCourse));
        commands.put("listCourse", new Command(new int[] { 0, 1 }, CommandHandler::listCourse));
        commands.put("selectCourse", new Command(new int[] { 1 }, CommandHandler::selectCourse));
        commands.put("cancelCourse", new Command(new int[] { 1 }, CommandHandler::cancelCourse));
        commands.put("outputCourseBatch", new Command(new int[] { 1 }, CommandHandler::outputCourseBatch));
        commands.put("inputCourseBatch", new Command(new int[] { 1 }, CommandHandler::inputCourseBatch));
        commands.put("listStudent", new Command(new int[] { 1 }, CommandHandler::listStudent));
        commands.put("removeStudent", new Command(new int[] { 1, 2 }, CommandHandler::removeStudent));
        commands.put("listCourseSchedule", new Command(new int[] { 0, 1 }, CommandHandler::listCourseSchedule));
        return commands;
    }

    public static void executeCommand(String command, String[] arguments) throws Exception {
        Command cmd = COMMANDS.get(command);
        if (cmd == null) {
            throw new IllegalArgumentException("Command '" + command + "' not found");
        }

        int argCount = arguments.length;
        if (Arrays.stream(cmd.expectedArgs).noneMatch(count -> count == argCount)) {
            throw new IllegalArgumentException("Illegal argument count");
        }

        cmd.action.accept(arguments);
    }

    private static void quit() {
        UserService.logoutAll();
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }

    private static void register(String[] args) throws Exception {
        String userID = args[0];
        String name = args[1];
        String password = args[2];
        String confirmPassword = args[3];
        String identity = args[4];

        UserService.register(userID, name, password, confirmPassword, identity);
    }

    private static void login(String[] args) throws Exception {
        String userID = args[0];
        String password = args[1];

        UserService.login(userID, password);
    }

    private static void logout(String[] args) throws Exception {
        if (args.length == 0) {
            UserService.logout();
        } else {
            UserService.logout(args[0]);
        }
    }

    private static void switchUser(String[] args) throws Exception {
        UserService.switchUser(args[0]);
    }

    private static void printInfo(String[] args) throws Exception {
        if (args.length == 0) {
            UserService.printInfo();
        } else {
            UserService.printInfo(args[0]);
        }
    }

    private static void createCourse(String[] args) throws Exception {
        String courseName = args[0];
        String courseTime = args[1];
        double credit;
        int period;

        try {
            credit = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Illegal course credit");
            return;
        }

        try {
            period = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            System.out.println("Illegal course period");
            return;
        }

        UserService.createCourse(courseName, courseTime, credit, period);
    }

    private static void listCourse(String[] args) throws Exception {
        UserService.listCourse(args);
    }

    private static void selectCourse(String[] args) throws Exception {
        UserService.selectCourse(args[0]);
    }

    private static void cancelCourse(String[] args) throws Exception {
        UserService.cancelCourse(args[0]);
    }

    private static void outputCourseBatch(String[] args) throws Exception {
        UserService.outputCourseBatch(args[0]);
    }

    private static void inputCourseBatch(String[] args) throws Exception {
        UserService.inputCourseBatch(args[0]);
    }

    private static void listStudent(String[] args) throws Exception {
        UserService.listStudent(args[0]);
    }

    private static void removeStudent(String[] args) throws Exception {
        UserService.removeStudent(args[0], Arrays.copyOfRange(args, 1, args.length));
    }

    private static void listCourseSchedule(String[] args) throws Exception {
        UserService.listCourseSchedule(args);
    }

    private static class Command {
        int[] expectedArgs;
        ThrowingConsumer<String[]> action;

        Command(int[] expectedArgs, ThrowingConsumer<String[]> action) {
            this.expectedArgs = expectedArgs;
            this.action = action;
        }
    }

    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws Exception;
    }
}