package services;

import except.RegistrationException;
import user.*;

import static services.UserService.addUser;
import static services.UserService.userExists;
import static utils.ValidationUtils.*;

public class UserRegistry {

    private static User createUser(String userID, String name, String password, String identity) {
        return switch (identity) {
            case "Student" -> new Student(userID, name, password);
            case "Teacher" -> new Teacher(userID, name, password);
            case "Administrator" -> new Administrator(userID, name, password);
            default -> null;
        };
    }

    protected static void register(String userID, String name, String password, String confirmPassword, String identity) throws RegistrationException {
        if (!isValidUserID(userID)) {
            throw new RegistrationException("Illegal user id");
        }

        if (userExists(userID)) {
            throw new RegistrationException("User id exists");
        }

        if (!isValidName(name)) {
            throw new RegistrationException("Illegal user name");
        }

        if (!isValidPassword(password)) {
            throw new RegistrationException("Illegal password");
        }

        if (!password.equals(confirmPassword)) {
            throw new RegistrationException("Passwords do not match");
        }

        User user = createUser(userID, name, password, identity);
        if (user == null) {
            throw new RegistrationException("Illegal identity");
        }

        addUser(user);
        System.out.println("Register success");
    }
}