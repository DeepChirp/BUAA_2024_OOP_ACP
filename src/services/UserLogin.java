package services;

import user.User;
import except.LoginException;

import static services.UserService.*;
import static utils.ValidationUtils.isValidUserID;

public class UserLogin {
    protected static void login(String userID, String password) throws LoginException {
        User user = getUser(userID);
        if (!isValidUserID(userID)) {
            throw new LoginException("Illegal user id");
        }
        if (user == null) {
            throw new LoginException("User does not exist");
        }
        if (isOnline(user)) {
            throw new LoginException(userID + " is online");
        }
        if (!user.getPassword().equals(password)) {
            throw new LoginException("Wrong password");
        }
        addOnlineUser(user);
        setCurrentUser(user);
        System.out.println("Welcome to ACP, " + user.getID());
    }
}
