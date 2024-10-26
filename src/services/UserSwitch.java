package services;

import except.SwitchUserException;
import static services.UserService.*;
import static utils.ValidationUtils.*;

public class UserSwitch {
    protected static void switchUser(String userID) throws SwitchUserException {
        if (!isValidUserID(userID)) {
            throw new SwitchUserException("Illegal user id");
        }

        if (!userExists(userID)) {
            throw new SwitchUserException("User does not exist");
        }

        if (!isOnline(getUser(userID))) {
            throw new SwitchUserException(userID + " is not online");
        }

        setCurrentUser(getUser(userID));
        System.out.println("Switch to " + userID);
    }
}
