import model.Student;
import model.StudentDAO;
import model.StudentDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/test",
                    "root",
                    "***"
            );

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from users");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("username"));
            }
        } catch (Exception ex) {
            System.out.println("exception");
        }

    }

    static void logSetup() {
        Logger logger = Logger.getLogger(TestClass.class.getName());

        // Create a ConsoleHandler and set its level and formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(new java.util.logging.SimpleFormatter());

        // Add the ConsoleHandler to the logger
        logger.addHandler(consoleHandler);
        logger.setLevel(Level.ALL);

        // Log messages
        logger.info("This is an info message.");
        logger.warning("This is a warning message.");
        logger.severe("This is a severe message.");
    }

    static void dbCheck() {
        StudentDAO studentDAO = new StudentDAOImpl();
        System.out.println(studentDAO.addStudent(new Student(1, "Rahul", "Reddaveni",
                "test@gmail.com", true)));
        System.out.println(studentDAO.addStudent(new Student(2, "Malavika", "Ajit",
                "test@gmail.com", true)));
        studentDAO.getAllStudents().forEach(System.out::println);
    }
}
