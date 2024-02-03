import java.sql.*;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC",
            DB_USER = "root",
            DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}