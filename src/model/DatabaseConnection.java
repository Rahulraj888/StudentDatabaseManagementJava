package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static config.LocalConfig.DB_PASSWORD;
import static config.LocalConfig.DB_URL;
import static config.LocalConfig.DB_USERNAME;

/**
 * Handles connection to database
 */
public class DatabaseConnection {

    private static DatabaseConnection databaseConnection; // singleton instance for database
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

//    Singleton pattern used for DB object creation
    public static DatabaseConnection getInstance() throws SQLException {
        if (databaseConnection == null || databaseConnection.getConnection().isClosed()) {
            databaseConnection = new DatabaseConnection();
        }

        return databaseConnection;
    }
}
