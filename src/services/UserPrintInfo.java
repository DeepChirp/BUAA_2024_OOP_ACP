package services;

import except.PrintInfoException;
import user.User;

import java.util.Objects;

import static services.UserService.*;
import static utils.ValidationUtils.isValidUserID;

public class UserPrintInfo {
    protected static void printInfo() throws PrintInfoException {
        if (noCurrentUser()) {
            throw new PrintInfoException("No one is online");
        }
        User user = getCurrentUser();
        System.out.println("User id: " + user.getID());
        System.out.println("Name: " + user.getName());
        System.out.println("Type: " + user.getRole());
        System.out.println("Print information success");
    }

    protected static void printInfo(String userID) throws PrintInfoException {
        if (noCurrentUser()) {
            throw new PrintInfoException("No one is online");
        }
        if (!Objects.equals(getCurrentUser().getRole(), "Administrator")) {
            throw new PrintInfoException("Permission denied");
        }
        if (!isValidUserID(userID)) {
            throw new PrintInfoException("Illegal user id");
        }
        User user = getUser(userID);
        if (user == null) {
            throw new PrintInfoException("User does not exist");
        }
        System.out.println("User id: " + user.getID());
        System.out.println("Name: " + user.getName());
        System.out.println("Type: " + user.getRole());
        System.out.println("Print information success");
    }
}
