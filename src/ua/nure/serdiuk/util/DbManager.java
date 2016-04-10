package ua.nure.serdiuk.util;

import ua.nure.serdiuk.entity.User;

import java.util.HashMap;
import java.util.Map;

public class DbManager {

    private static final Map<String, User> USERS = new HashMap<>();

    static {
        USERS.put("daria", new User("daria", "1234"));
        USERS.put("alex", new User("alex", "1234"));
    }

    public static User auth(String login, String password) {
        User user = USERS.get(login);

        if (user == null) {
            return null;
        }
        return user.getPassword().equals(password) ? user :
                null;
    }
}
