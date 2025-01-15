package DB.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import Utils.PasswordUtils;

@DatabaseTable(tableName = "User")
public class User {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String username;

    @DatabaseField
    private String hashedPassword;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = PasswordUtils.hashPassword(password);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = PasswordUtils.hashPassword(password);
    }

}
