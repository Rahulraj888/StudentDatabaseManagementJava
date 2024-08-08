package model;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import static config.LocalConfig.DB_PASSWORD;
import static config.LocalConfig.DB_URL;
import static config.LocalConfig.DB_USERNAME;
import static java.util.logging.Level.SEVERE;

/**
 * Creates connection with database.
 * singleton patter is followed for object creation
 */
@Getter
public class DatabaseConnection {

    private static DatabaseConnection databaseConnection; // singleton instance for database
    private Connection connection;
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException ex) {
            logger.log(SEVERE, "Exception occurred while adding student details to database ", ex);
        }
    }

    //    Singleton pattern used for DB object creation
    public static DatabaseConnection getInstance() throws SQLException {
        if (databaseConnection == null || databaseConnection.getConnection().isClosed()) {
            databaseConnection = new DatabaseConnection();
        }

        return databaseConnection;
    }
}
