package Services;
import DB.Dao.UserDao;
import DB.DatabaseConnection;
import DB.Models.User;

import java.sql.SQLException;

public class UserService {

    UserDao userDao = new UserDao(new DatabaseConnection().getConnectionSource(), User.class);

    public UserService() throws Exception {
    }

    public void addUser(String username, String password) {
        try {
            userDao.addUser(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkUserAlreadyExists(String username) {
        try {
            return userDao.checkUserAlreadyExists(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkForLogin(String username, String password) {
        try {
            return userDao.checkForLogin(username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserForLogin(String username) {
        try {
            return userDao.getUserForLogin(username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        UserService us = new UserService();
        us.addUser("admin", "password");
        System.out.println(us.checkForLogin("admin", "password"));
    }
}
