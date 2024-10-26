package services;

import user.User;
import except.LogoutException;

import java.util.Objects;

import static services.UserService.*;
import static utils.ValidationUtils.isValidUserID;

public class UserLogout {
    protected static void logout() throws LogoutException {
        if (noCurrentUser()) {
            throw new LogoutException("No one is online");
        }
        String userID = getCurrentUser().getID();
        removeOnlineUser(getCurrentUser());
        setCurrentUser(null);
        System.out.println(userID + " Bye~");
    }

    protected static void logout(String userID) throws LogoutException {
        if (noCurrentUser()) {
            throw new LogoutException("No one is online");
        }
        if (!Objects.equals(getCurrentUser().getRole(), "Administrator")) {
            throw new LogoutException("Permission denied");
        }
        if (!isValidUserID(userID)) {
            throw new LogoutException("Illegal user id");
        }
        User user = getUser(userID);
        if (user == null) {
            throw new LogoutException("User does not exist");
        }
        if (!isOnline(user)) {
            throw new LogoutException(userID + " is not online");
        }
        removeOnlineUser(user);
        System.out.println(userID + " Bye~");
    }
}
