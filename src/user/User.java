package user;

import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String userID;
    protected String name;
    protected String password;

    public User(String userID, String name, String password) {
        this.userID = userID;
        this.name = name;
        this.password = password;
    }

    public String getID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return this.getClass().getSimpleName();
    }
}