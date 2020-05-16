package controller;

import dao.UserDao;
import model.User;

import java.util.Optional;

public class UserController {

    private UserDao userDao;

    private UserController() {
        userDao = new UserDao(ConnectionManager.getInstance().getConnection());
    }

    private static final class SingletonHolder {
        private static final UserController INSTANCE = new UserController();
    }

    public static UserController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public boolean registration(User user) {
        Optional<User>  userOptional = userDao.findUser(user.getUsername());
        if (userOptional.isEmpty()) {
            userDao.addUser(user);
            return true;
        }
        return false;
    }

    public Optional<User> login(String username, String password) {
        Optional<User>  userOptional = userDao.findUser(username);
        if (!userOptional.isEmpty()) {
            if (userOptional.get().getPassword().equals(password)) {
                return userOptional;
            }
        }
        return Optional.empty();
    }

}
