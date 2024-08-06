package constants;

/**
 * constants declared for table and column names
 */
public class DatabaseConstants {

    public static final String TABLE_NAME = "students";
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String ACTIVE = "active";

    //prevent object creation
    private DatabaseConstants() {
    }
}