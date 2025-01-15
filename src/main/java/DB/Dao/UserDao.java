package DB.Dao;

import DB.Models.User;
import Exceptions.WrongPasswordException;
import Exceptions.WrongUsernameException;
import Utils.PasswordUtils;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class UserDao extends BaseDaoImpl<User, Integer> {

    public UserDao(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
        TableUtils.createTableIfNotExists(connectionSource, User.class);
    }

    public void addUser(String username, String password) throws SQLException {
        this.create(new User(username, password));
    }

    public boolean checkUserAlreadyExists(String username) throws SQLException {
        User user = this.queryBuilder().where().eq("username", username).queryForFirst();
        return user != null;
    }

    public boolean checkForLogin(String username, String password) throws SQLException {
        User user = this.queryBuilder().where().eq("username", username).queryForFirst();
        if (user == null) {
            throw new WrongUsernameException("Не нашли пользователя с таким именем");
        }
        if (!PasswordUtils.checkPassword(password, user.getHashedPassword())) {
            throw new WrongPasswordException("Не верный пароль");
        };
        return true;
    }

    public User getUserForLogin(String username) throws SQLException {
        return this.queryBuilder().where().eq("username", username).queryForFirst();
    }
}
