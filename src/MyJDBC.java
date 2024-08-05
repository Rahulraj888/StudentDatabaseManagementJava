import java.rmi.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MyJDBC {

    public static void main(String[] args){

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test",
                    "root",
                    "***"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from users");
            while(resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception ex) {
            System.out.println("exception");
        }

    }
}
