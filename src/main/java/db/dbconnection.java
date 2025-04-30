package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Paths;

public class dbconnection {
    private static final String DB_PATH = Paths.get("src", "data", "database.db").toString();
    private static final String URL = "jdbc:sqlite:" + DB_PATH;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
