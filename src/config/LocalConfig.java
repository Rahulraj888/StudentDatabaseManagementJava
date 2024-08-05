package config;

public class LocalConfig {

    private static final String DATABASE_NAME = "studentmanagement";
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/" + DATABASE_NAME;
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "***";

    //prevent object creation
    private LocalConfig() {
    }
}
