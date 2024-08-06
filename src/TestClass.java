import model.Student;
import model.StudentDAO;
import model.StudentDAOImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestClass {

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

    static void dbCheck() {
        StudentDAO studentDAO = new StudentDAOImpl();
        System.out.println(studentDAO.addStudent(new Student(1, "Rahul", "Reddaveni",
                "test@gmail.com", true)));
        System.out.println(studentDAO.addStudent(new Student(2, "Malavika", "Ajit",
                "test@gmail.com", true)));
        studentDAO.getAllStudents().forEach(System.out::println);
    }
}
