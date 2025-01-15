package DB;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

public class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:myfinance.db";

    public static ConnectionSource getConnectionSource() throws Exception {
        return new JdbcConnectionSource(DATABASE_URL);
    }
}
